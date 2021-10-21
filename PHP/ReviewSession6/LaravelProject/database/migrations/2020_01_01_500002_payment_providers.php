<?php

use App\Services\Payments\Providers\QiwiProvider;
use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Schema;
use App\Models\PaymentProvider;

class PaymentProviders extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('payment_providers', function (Blueprint $table) {
            $table->string('ident', 32);
            $table->primary('ident');

            $table->string('name', 64);

            $table->enum('status', [
                PaymentProvider::STATUS_ACTIVE,
                PaymentProvider::STATUS_DISABLED,
            ]);

            $table->string('classname', 256);

            $table->engine = 'InnoDB';
            $table->charset = 'utf8mb4';
            $table->collation = 'utf8mb4_unicode_ci';
        });

        DB::table('payment_providers')->insert(
            [
                'ident'     => 'qiwi',
                'name'      => 'Qiwi',
                'status'    => PaymentProvider::STATUS_ACTIVE,
                'classname' => QiwiProvider::class
            ]
        );
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('payment_providers');
    }
}
