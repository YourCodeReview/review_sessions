import pytest
from crypto_address_validator.validators import atom_validator as atomv


@pytest.mark.valid
@pytest.mark.address
@pytest.mark.atom
@pytest.mark.parametrize(
    'address',
    [
        'cosmos16na5gpcj80tafv5gycm4gk7garj8jjsgydtkmj',
        'cosmos1la4t7jee73a4zqg9ccs2acjt462qjs5xkls4n7',
        'cosmos1wl34gmc5taa5h8dcr8rs3cmjdu7h0cy5cm2e39',
    ]
)
def test_address_is_valid(address):
    assert atomv.is_valid_address(address)


@pytest.mark.invalid
@pytest.mark.address
@pytest.mark.atom
@pytest.mark.parametrize(
    'address',
    [
        'cosmos16na5gpcj80tafv5gycm4gk7garj8jjsgydtkmq',
        'cosmos',
        '6026aeb850f355f63778fe86fefa9cf670e89e6d2a0a2be992c34c12009fa3ab',
        '12345',
        'qwerty',
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
    assert not atomv.is_valid_address(address)
