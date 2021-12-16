using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class GeneratorObjects : MonoBehaviour
{
    [SerializeField] private GameObject[] _arrayGameObjects;
    [SerializeField] private float _intervalCreateObject;
    private float _timer;
    private int _countObjects;

    private System.Random _rnd = new System.Random();

    private void Start()
    {
        _countObjects = _arrayGameObjects.Length;
        _timer = _intervalCreateObject;
    }

    private void Update()
    {
        _timer -= Time.deltaTime;
        if (_timer < 0)
        {
            DumpingObject();
            _timer = _intervalCreateObject;
        }
    }

    private void DumpingObject()
    {
        GameObject droppedObject = _arrayGameObjects[_rnd.Next(_countObjects)];        
        droppedObject.transform.position = transform.position;
        Instantiate(droppedObject);
    }
}
