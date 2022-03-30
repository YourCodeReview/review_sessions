<?php

use PHPUnit\Framework\TestCase;
use FileAudit\DirectoryAudit;

class DirectoryAuditTest extends TestCase
{
    public function testListDirectory()
    {
        $dirname = 'test/test';
        $list = scandir($dirname);

        $sut = new DirectoryAudit();
        $this->assertSame(count($list) - 2, count($sut->getListFileNames($dirname)));
    }
}