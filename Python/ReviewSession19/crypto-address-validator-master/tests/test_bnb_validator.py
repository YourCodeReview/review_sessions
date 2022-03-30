import pytest
from crypto_address_validator.validators import bnb_validator as bnbv


@pytest.mark.valid
@pytest.mark.address
@pytest.mark.bnb
@pytest.mark.parametrize(
    'address',
    [
        # mainnet
        'bnb1hrku3lxq2mpcl6e7jdhxlpkc0xglq3el3jjns4',
        'bnb17fk9lwcve6vufcc8tflwvref878myw9mgy3ewn',
        'bnb13eekke9gt5msqpcu3hxek3e9q5v6xk448464wz',

        # testnet
        'tbnb1hexqyu3m8uuudqdnnpnsnlwe6xg0n3078lx68l',
        'tbnb1r6l0c0fxu458hlq6m7amkcltj8nufyl9mr2wm5',
        'tbnb135mqtf9gef879nmjlpwz6u2fzqcw4qlzrqwgvw'
    ]
)
def test_address_is_valid(address):
    assert bnbv.is_valid_address(address)


@pytest.mark.invalid
@pytest.mark.address
@pytest.mark.bnb
@pytest.mark.parametrize(
    'address',
    [
        'bnb1hrku3lxq2mpcl6e7jdhxlpkc0xglq3el3jjns',
        'bnb1hrku3lxq2mpcl6e7jdhxlpkc0xglq3el3jjns4q',
        'bnb1hrku3lxq2mpcl6e7jdhxlpkc0xglq3el3jjns1',
        '17fk9lwcve6vufcc8tflwvref878myw9mgy3ewn',
        'tbnb1hexqyu3m8uuudqdnnpnsnlwe6xg0n3078lx68l1',
        'tbnb1r6l0c0fxu458hlq6m7amkcltj8nufyl9mr2wm',
        'tbnb135mqtf9gef879nmjlpwz6u2fzqcw4qlzrqwgvq'
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
    assert not bnbv.is_valid_address(address)
