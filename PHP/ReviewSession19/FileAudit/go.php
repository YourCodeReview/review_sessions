<?php
require_once __DIR__ . '/vendor/autoload.php';

use FileAudit\FileLineRecord;
use FileAudit\DirectoryAudit;
use FileAudit\FileAudit;



$record = new FileLineRecord('test', date('Y-m-d H:i:s'));
$audit = new FileAudit();
//$audit->addRecord($record, 'test%.txt', 'test', 3, new DirectoryAudit());
