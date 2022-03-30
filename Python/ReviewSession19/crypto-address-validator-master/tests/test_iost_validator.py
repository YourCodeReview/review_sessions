import pytest
from crypto_address_validator.validators import iost_validator as iostv


@pytest.mark.valid
@pytest.mark.address
@pytest.mark.iost
@pytest.mark.parametrize(
    'address',
    [
        'base.iost',
        'iostliebibp',
        'infstones',
        'neoply_iost',
    ]
)
def test_address_is_valid(address):
    assert iostv.is_valid_address(address)


@pytest.mark.invalid
@pytest.mark.address
@pytest.mark.iost
@pytest.mark.parametrize(
    'address',
    [
        'qwertyqwertyqwerty',
        '8mc.',
        'Lqqdouge',
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
    assert not iostv.is_valid_address(address)
