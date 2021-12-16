using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Ghost : MonoBehaviour
{
    private GameObject _superMan;
    private Vector3 _targetPos;
    [SerializeField] float _power;
    private Vector3 _direction;
    private Rigidbody _gameObjectRigid;


    void Start()
    {
        _superMan = GameObject.FindGameObjectWithTag("SuperMan");
        _targetPos = _superMan.transform.position - transform.position;
        _gameObjectRigid = GetComponent<Rigidbody>();
        _direction = _targetPos.normalized * _power;
        _gameObjectRigid.velocity = _direction;

    }

}
