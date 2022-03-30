<?php
namespace FileAudit;

class FileLineRecord implements FileRecordable {
    const RECORD_SEPARATOR = "\r\n";

    public function __construct(string $data, string $date)
    {
        $this->data = $data;
        $this->date = $date;
    }

    public static function getCountRecord(string $content): int {
        if (empty($content)) {
            return 0;
        }
        $tmp = explode(self::RECORD_SEPARATOR, $content);
        $cnt = count($tmp);
        return $cnt;
    }

    public function createRecord(): string {
        return $this->data . ';' . $this->date;
    }

    public function addRecord(string $content, string $record): string {
        $content = $content ? $content . self::RECORD_SEPARATOR . $record : $record;
        return $content;
    }
}