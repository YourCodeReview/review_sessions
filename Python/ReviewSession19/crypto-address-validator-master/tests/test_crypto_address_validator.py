import pytest
from crypto_address_validator import validate


@pytest.mark.symbol
def symbol_is_valid(symbol: str):
    validators = {
        'btc': ...
    }

    try:
        validators[symbol]
    except (TypeError, KeyError):
        print(f'"{symbol}" currency is not supported.')
        return False


def test_symbol_is_valid():
    assert symbol_is_valid('btc') is None


@pytest.mark.symbol
@pytest.mark.parametrize(
    'symbol',
    [
        ' ',
        '',
        123,
        b'abc',
        str,
        None,
        True,
        ('31hr5x7HpgUTNJsdukGEUmjNNTiyVr9aT1', 'btc', 'mainnet'),
        ('address', 'symbol', 'network'),
        'bnb'
    ]
)
def test_symbol_is_invalid(symbol, capfd):
    symbol_is_valid(symbol)
    out, err = capfd.readouterr()
    assert out == f'"{symbol}" currency is not supported.\n'


@pytest.mark.valid
@pytest.mark.address
@pytest.mark.parametrize(
    'address',
    [
        '31hr5x7HpgUTNJsdukGEUmjNNTiyVr9aT1',
        'bc1q7w9p2unf3hngtzmq3sq47cjdd0xd9sw7us5h6p',
        'bc1qc7slrfxkknqcq2jevvvkdgvrt8080852dfjewde450xdlk4ugp7szw5tk9',
    ]
)
def test_address_is_valid(address):
    assert validate('btc', address)


@pytest.mark.invalid
@pytest.mark.address
@pytest.mark.parametrize(
    'address',
    [
        ' ',
        '',
        123,
        b'abc',
        str,
        None,
        True,
        ('31hr5x7HpgUTNJsdukGEUmjNNTiyVr9aT1', 'btc', 'mainnet'),
        ('address', 'symbol', 'network'),
        'bnb'
        '31hr5x7HpgUTNJsdukGEUmjNNTiyVr9aT',
        'bc1q7w9p2unf3hngtzmq3sq47cjdd0xd9sw7us5h6pq',
        'bc1qc7slrfxkknqcq2jevvvkdgvrt8080852dfjewde450xdlk4ugp7szw5tkx',
        '12345',
        'qwerty',
    ]
)
def test_address_is_invalid(address):
    assert not validate('btc', address)
