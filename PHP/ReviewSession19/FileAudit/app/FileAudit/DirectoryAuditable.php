<?php
namespace FileAudit;

interface DirectoryAuditable {
    public function getListFileNames(string $directoryName, string $fileTemplate = '');
    public function createFile(string $fileName);
    public function getFileContent(string $fileName): string;
    public function saveFileContent(string $fileName, string $content);
}