using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class OnSound : MonoBehaviour
{
    private bool _status = true;

    public void ActiveSound()
    {
        if (_status == true)
        {
            _status = false;
            AudioListener.volume = 0;
        }
        else
        {
            _status = true;
            AudioListener.volume = 1;
        }
    }
}
