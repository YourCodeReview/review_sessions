import pytest
from crypto_address_validator.validators import aion_validator as aionv


@pytest.mark.valid
@pytest.mark.address
@pytest.mark.aion
@pytest.mark.parametrize(
    'address',
    [
        # mainnet
        '0xa0c24fbbecf42184d1ca8e9401ddaa2a99f69f3560e3d6c673de3c8a0be2a8eb',
        '0xa008e42a76e2e779175c589efdb2a0e742b40d8d421df2b93a8a0b13090c7cc8',
        '0xa00983f07c11ee9160a64dd3ba3dc3d1f88332a2869f25725f56cbd0be32ef7a',
        '0xa06ed7c5bc9aedb0f9fccdb431363a66d0a7f0ba334697468dcf826a6aee9a69',
        '0xa001f738ac5b5a9d0c4883ac08fb37fe33b6f32143163d96a7f7c9d82a2b4ccc',
        'a01d2fb2c000fcd6f934495b7c9c2b94eedbfb0f5edbfcd0f0d4938abd87da83',

        # testnet
        '0xa0178543726d2018431e51e68815c4419dbbc6f172908be3792496e001d8a54e',
        '0xa0a7707f5bf4a257cf2f582e5a8be3c5a869bcf47ed6801517635edf4d279cb7',
        '0xa0bc56fa67275de02841bfe21043c153b6eabfd095b3b9067ee531070ede8ce5'
    ]
)
def test_address_is_valid(address):
    assert aionv.is_valid_address(address)


@pytest.mark.invalid
@pytest.mark.address
@pytest.mark.aion
@pytest.mark.parametrize(
    'address',
    [
        '0xa0c24fbbecf42184d1ca8e9401ddaa2a99f69f3560e3d6c673de3c8a0be2a8ebq',
        '0xa008e42a76e2e779175c589efdb2a0e742b40d8d421df2b93a8a0b13090c7cc',
        'a01d2fb2c000fcd6f934495b7c9c2b94eedbfb0f5edbfcd0f0d4938abd87da8',
        '1xa0178543726d2018431e51e68815c4419dbbc6f172908be3792496e001d8a54e1',
        '0xa0a7707f5bf4a257cf2f582e5a8be3c5a869bcf47ed6801517635edf4d279cb',
        '0xa0178543726d2018431e51e68815c4419dbbc6f172908be3792496e001d8a542e',
        '0xa0bc56fa67275de02841bfe21043c153b6eabfd095b3b9067ee531070ede8ce'
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
    assert not aionv.is_valid_address(address)
