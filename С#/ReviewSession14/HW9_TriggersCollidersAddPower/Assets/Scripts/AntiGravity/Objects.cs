using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Objects : MonoBehaviour
{
    [SerializeField] private float _minBoardY;


    private void Update()
    {
        if (gameObject.transform.position.y <= _minBoardY)
        {
            Destroy(gameObject);
        }
    }

}
