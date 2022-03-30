<?php
namespace FileAudit;

class FileAudit implements FileAuditable {
    const TEMPLATE_SIGN = '%';

    public function __construct()
    {
    }

    public function addRecord(FileRecordable $contentMaker, string $fileNameTemplate, string $directoryName, int $maxRecordInFile, DirectoryAuditable $directoryManager)
    {
        $filename = $this->getCurrentFileOrCreate($fileNameTemplate, $directoryName, $maxRecordInFile, $directoryManager, $contentMaker);

        $record = $contentMaker->createRecord();
        $content = $directoryManager->getFileContent($filename);
        $content = $contentMaker->addRecord($content, $record);

        $directoryManager->saveFileContent($filename, $content);
    }

    public function getCurrentFileOrCreate(string $fileNameTemplate, string $directoryName, int $maxRecordInFile, DirectoryAuditable $directoryManager, FileRecordable $contentMaker):string {
        $index = 0;
        $list = $directoryManager->getListFileNames($directoryName, $fileNameTemplate);

        if (empty($list)) {
            $filename = $this->getFullFileName($directoryName, $index, $fileNameTemplate);
            $directoryManager->createFile($filename);
        } else {
            $index = $this->getLastFileIndex($list, $fileNameTemplate);
            $filename = $this->getFullFileName($directoryName, $index, $fileNameTemplate);
            $content = $directoryManager->getFileContent($filename);
            if ($contentMaker->getCountRecord($content) >= $maxRecordInFile) {
                $index++;
                $filename = $this->getFullFileName($directoryName, $index, $fileNameTemplate);
                $directoryManager->createFile($filename);
            }
        }

        return $filename;
    }

    public function getLastFileIndex($list, string $fileNameTemplate):int {
        $parts = explode(self::TEMPLATE_SIGN, $fileNameTemplate);
        $max = 0;
        foreach($list as $item) {
            $index = intval(str_replace($parts, '', $item));
            $max = $index > $max ? $index : $max;
        }

        return $max;
    }

    public function getFullFileName(string $directoryName, int $index, string $fileNameTemplate): string {
        return  $directoryName . DIRECTORY_SEPARATOR. str_replace(self::TEMPLATE_SIGN, $index, $fileNameTemplate);
    }
}
?>