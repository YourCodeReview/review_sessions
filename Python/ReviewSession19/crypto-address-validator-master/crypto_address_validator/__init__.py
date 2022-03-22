from crypto_address_validator.validators import default_validator
from crypto_address_validator.validators import atom_validator
from crypto_address_validator.validators import bnb_validator
from crypto_address_validator.validators import aion_validator
from crypto_address_validator.validators import eos_validator
from crypto_address_validator.validators import iost_validator
from crypto_address_validator.validators import miota_validator


validators = {
    'btc': default_validator,
    'atom': atom_validator,
    'bnb': bnb_validator,
    'aion': aion_validator,
    'eos': eos_validator,
    'iost': iost_validator,
    'miota': miota_validator
}


def validate(symbol: str, address: str) -> bool:
    """Validates the address of the passed symbol.

    Args:
        symbol (str): Currency symbol, e.g. 'btc' or 'atom'.
        address (str): Currency address to validate.

    Returns:
        bool: Result of address validation.
    """
    try:
        validator = validators[symbol]
    except (TypeError, KeyError):
        print(f'"{symbol}" currency is not supported.')
        return False

    if not isinstance(address, str):
        return False

    # passes the address to the appropriate validator
    return validator.is_valid_address(address)
