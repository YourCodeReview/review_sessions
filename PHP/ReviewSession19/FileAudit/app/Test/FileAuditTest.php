<?php

use PHPUnit\Framework\TestCase;
use FileAudit\FileAudit;
use FileAudit\DirectoryAudit;
use FileAudit\FileLineRecord;

class FileAuditTest extends TestCase
{
    public function testGetLastFileIndex()
    {
        $list = [];
        $templ = 'audit%.txt';
        $sut = new FileAudit();
        $this->assertSame(0, $sut->getLastFileIndex($list, $templ));

        $list = ['tets', 'rsnh'];
        $this->assertSame(0, $sut->getLastFileIndex($list, $templ));

        $list = ['audit5.txt', 'audit1.txt', 'audit4.txt'];
        $this->assertSame(5, $sut->getLastFileIndex($list, $templ));

    }

    public function test_Get_0index_file_in_empty_dir()
    {
        $directoryName = 'test_empty';
        $fileNameTemplate = 'test%.txt';
        $maxRecordInFile = 2;
        $directoryManager = new DirectoryAudit();
        $contentMaker = new FileLineRecord('data', date('Y-m-d'));

        $sut = new FileAudit();
        $filename = $sut->getCurrentFileOrCreate( $fileNameTemplate,  $directoryName,  $maxRecordInFile,  $directoryManager,  $contentMaker);
        $this->assertSame($directoryName.'/test0.txt', $filename);
    }

    public function test_Create_nextindex_file()
    {
        $directoryName = 'test/test_full';
        $fileNameTemplate = 'test%.txt';
        $maxRecordInFile = 2;
        $directoryManager = new DirectoryAudit();
        $contentMaker = new FileLineRecord('data', date('Y-m-d'));
        file_put_contents($directoryName.'/test0.txt', 'data0;'.date('Y-m-d').FileLinerecord::RECORD_SEPARATOR.'data1;'.date('Y-m-d'));

        $sut = new FileAudit();
        $filename = $sut->getCurrentFileOrCreate( $fileNameTemplate,  $directoryName,  $maxRecordInFile,  $directoryManager,  $contentMaker);
        $this->assertSame($directoryName.'/test1.txt', $filename);
    }

    public function test_Add_record()
    {
        $date = date('Y-m-d');
        $directoryName = 'test/test_add';
        $fileNameTemplate = 'testmy%my.txt';
        $maxRecordInFile = 2;
        $directoryManager = new DirectoryAudit();
        $contentMaker = new FileLineRecord('data1', $date);
        file_put_contents($directoryName.'/testmy0my.txt', 'data0;'.$date);

        $sut = new FileAudit();
        $sut->addRecord($contentMaker, $fileNameTemplate,  $directoryName,  $maxRecordInFile,  $directoryManager);
        $this->assertSame(file_get_contents($directoryName.'/testmy0my.txt'), 'data0;'.$date.FileLinerecord::RECORD_SEPARATOR.'data1;'.$date);
    }
}