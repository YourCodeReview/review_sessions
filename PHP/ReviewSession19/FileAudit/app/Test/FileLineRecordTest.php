<?php

use PHPUnit\Framework\TestCase;
use FileAudit\FileLineRecord;

class FileLineRecordTest extends TestCase
{
    public function testCreateRecord()
    {
        $data = 'data';
        $date = date('Y-m-d');
        $sut = new FileLineRecord($data, $date);
        $this->assertSame($data . ';' . $date, $sut->createRecord());
    }

    public function testAddRecord()
    {
        $data = 'data';
        $date = date('Y-m-d');
        $sut = new FileLineRecord($data, $date);

        $rec1 = new FileLineRecord('add 1', $date);
        $rec2 = new FileLineRecord('add 2', $date);

        $record1 = $rec1->createRecord();
        $record2 = $rec2->createRecord();

        $content = $sut->createRecord();
        $content = $sut->addRecord($content, $record1);
        $content = $sut->addRecord($content, $record2);

        $this->assertSame($content, 'data;'.$date . FileLineRecord::RECORD_SEPARATOR . 'add 1;'.$date . FileLineRecord::RECORD_SEPARATOR . 'add 2;'.$date);
    }

    public function testCountRecord()
    {
        $data = 'data';
        $date = date('Y-m-d');

        $sut = new FileLineRecord($data, $date);
        $rec1 = (new FileLineRecord($data, $date))->createRecord();
        $rec2 = (new FileLineRecord($data, $date))->createRecord();

        $content = $sut->addRecord($sut->createRecord(), $rec1);
        $content = $sut->addRecord($content, $rec2);

        $this->assertSame(3, $sut->getCountRecord($content));
    }
}
