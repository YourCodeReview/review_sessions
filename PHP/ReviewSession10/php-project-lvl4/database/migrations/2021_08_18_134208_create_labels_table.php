<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateLabelsTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        $now = now();
        Schema::create('labels', function (Blueprint $table) use ($now) {
            $table->id();
            $table->string('name');
            $table->string('description')->nullable();
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
        Schema::dropIfExists('labels');
    }
}
