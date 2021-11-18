<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;
use App\Models\Task;
use App\Models\Label;
class CreateTaskLabelsTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        $now = now();
        Schema::create('task_label', function (Blueprint $table) use ($now) {
            $table->id();
            $table->foreignId('task_id')->references('id')->on('tasks');
            $table->foreignId('label_id')->references('id')->on('labels');
            $table->timestamp('created_at')->default($now);
            $table->timestamp('updated_at')->default($now);
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('task_label');
    }
}
