import pytest
from crypto_address_validator.validators import eos_validator as eosv


@pytest.mark.valid
@pytest.mark.address
@pytest.mark.eos
@pytest.mark.parametrize(
    'address',
    [
        'eosio.stake',
        'rptrxwgfeqyn',
        'binancecold1',
        'b1',
    ]
)
def test_address_is_valid(address):
    assert eosv.is_valid_address(address)


@pytest.mark.invalid
@pytest.mark.address
@pytest.mark.eos
@pytest.mark.parametrize(
    'address',
    [
        'rptrxwgfeqynrptrxwgfeqynrptrxwgfeqyn',
        '1.0.12345678',
        'foo6.bar',
        'rptrxwgfeqyn.',
        'qwertyqwertyqwerty'
        'cosmos',
        '',
        '31hr5x7HpgUTNJsdukGEUmjNNTiyVr9aT',
        'bc1qnpgqxy7nq7zt6snx0kn76lv3z6xz5dtceupg401',
        'мой биткоин адрес',
        '比特币地址',
        'Àåæ´ýú.ü.£ßòÒÀì£Ê·¸®±ô¿¯åÇÊ¯¯ï',
        '-[:%/~%`!,)+~;{.\\-.%.**_[(\\$".'
    ]
)
def test_address_is_invalid(address):
    assert not eosv.is_valid_address(address)
