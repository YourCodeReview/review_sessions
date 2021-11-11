from peewee import (AutoField, BooleanField, CharField, DateField,
                    DecimalField, ForeignKeyField, IntegerField, Model,
                    SqliteDatabase)


class BaseModel(Model):
    """Base model for DB connection"""

    id = AutoField()

    class Meta:
        database = SqliteDatabase("db.sqlite3")


class Fine(BaseModel):
    """Fines data"""

    uin = CharField(max_length=25, null=True)
    number = CharField(max_length=18, null=True)
    debtor = CharField(null=True)
    debtor_code = CharField(null=True)


class BasePayment(BaseModel):
    """Base payment model for inheritance"""

    date = DateField()
    num = IntegerField()
    amount = DecimalField(max_digits=9, decimal_places=2, null=True)
    payer_code = CharField(null=True)
    payer = CharField(null=True)
    fine = ForeignKeyField(Fine, null=True)


class SinglePayment(BasePayment):
    """Single payment model"""

    elem_id = IntegerField(null=True)
    is_taken = BooleanField(null=True)


class RegisterPayment(BasePayment):
    """Register payment model"""

    payorder_id = IntegerField()


# Fine.create_table()
# SinglePayment.create_table()
# RegisterPayment.create_table()