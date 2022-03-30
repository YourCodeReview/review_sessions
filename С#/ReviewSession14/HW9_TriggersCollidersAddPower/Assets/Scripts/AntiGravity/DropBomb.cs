using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class DropBomb : MonoBehaviour
{
    [SerializeField] private GameObject _prafabBomb;
    private GameObject _bomb;
    private bool _haveBomb = false;
    public bool HaveBomb
    {
        set { _haveBomb = value; }
    }


    public void DropBombFromHatch()
    {
        _bomb = _prafabBomb;
        
        _bomb.transform.position = transform.position;
        if (_haveBomb == false)
        {
            Instantiate(_bomb);
            _haveBomb = true;
        }
    }
}
