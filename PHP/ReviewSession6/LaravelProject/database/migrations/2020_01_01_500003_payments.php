<?php

use App\Services\Payments\IPayment;
use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class Payments extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('payments', function (Blueprint $table) {
            $table->bigIncrements('id');

            $table->string('provider_ident', 32);
            $table->foreign('provider_ident')
                ->references('ident')
                ->on('payment_providers')
                ->onDelete('restrict')
                ->onUpdate('cascade');

            $table->foreignUuid('order_id');

            $table->enum('status', [
                IPayment::STATUS_PENDING,
                IPayment::STATUS_PROCESSED,
                IPayment::STATUS_ERROR,
            ]);

            $table->text('data')
                ->nullable();

            $table->text('error')
                ->nullable();

            $table->dateTime('created_at')
                ->useCurrent();

            $table->dateTime('updated_at')
                ->nullable();

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
        Schema::dropIfExists('payments');
    }
}
