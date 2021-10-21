<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;
use App\Services\Payments\IOrder;

class Orders extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('orders', function (Blueprint $table) {
            $table->bigIncrements('id');

            $table->uuid('ident');

            $table->unsignedInteger('user_id');
            $table->foreign('user_id')
                ->references('id')
                ->on('users')
                ->onDelete('restrict')
                ->onUpdate('cascade');

            $table->decimal('price', 8, 2)
                ->unsigned();

            $table->string('currency', 4);

            $table->string('comment', 512);

            $table->decimal('value', 8, 2)
                ->unsigned();

            $table->enum('status', [
                IOrder::STATUS_PENDING,
                IOrder::STATUS_SUCCESS,
                IOrder::STATUS_FAIL,
                IOrder::STATUS_EXPIRED,
            ]);

            $table->text('error')
                ->nullable();

            $table->dateTime('created_at')
                ->useCurrent();

            $table->dateTime('updated_at')
                ->nullable();

            $table->dateTime('expires_at')
                ->nullable(false);

            $table->unique('ident');

            $table->engine = 'InnoDB';
            $table->charset = 'utf8mb4';
            $table->collation = 'utf8mb4_unicode_ci';
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('orders');
    }
}
