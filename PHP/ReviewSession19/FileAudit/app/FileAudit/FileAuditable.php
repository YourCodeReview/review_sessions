<?php
namespace FileAudit;

interface FileAuditable {
    public function addRecord(FileRecordable $contentMaker, string $fileNameTemplate, string $directoryName, int $maxRecordInFile, DirectoryAuditable $directoryManager);
}