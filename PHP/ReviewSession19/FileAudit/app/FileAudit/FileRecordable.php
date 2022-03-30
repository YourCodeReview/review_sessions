<?php
namespace FileAudit;

interface FileRecordable {
    public static function getCountRecord(string $content): int;
    public function createRecord(): string;
    public function addRecord(string $content, string $record): string;
}