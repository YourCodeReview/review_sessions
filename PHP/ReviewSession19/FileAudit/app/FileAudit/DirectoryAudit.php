<?php
namespace FileAudit;

class DirectoryAudit implements DirectoryAuditable {

    public function __construct()
    {
    }

    public function getListFileNames(string $directoryName, string $fileTemplate = '') {
        if (!is_dir($directoryName)) {
            mkdir($directoryName);
            //throw new \Exception('Directory ' . $directoryName . ' does not exist');
        }
        $list = scandir($directoryName, SCANDIR_SORT_ASCENDING);
        foreach($list as $key=>$name) {
            if (in_array($name, ['.', '..'])) {
                unset($list[$key]);
            }
        }
        return $list;
    }

    public function createFile(string $fileName) {
        file_put_contents($fileName, '');
    }

    public function getFileContent(string $fileName):string {
        return file_get_contents($fileName);
    }

    public function saveFileContent(string $fileName, string $content) {
        file_put_contents($fileName, $content);
    }
}