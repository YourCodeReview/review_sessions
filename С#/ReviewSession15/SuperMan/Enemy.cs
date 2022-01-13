using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Enemy : MonoBehaviour
{
    [SerializeField] float _speed;
    [SerializeField] float _pause;
    private float _timer;
    private GameObject[] _men;
    private GameObject _man;
    private Vector3 _target;
    private Vector3 _position;
    private bool _getStrike = false;
    public bool GetStrike
    {
        set { _getStrike = value; }
    }


    System.Random rnd = new System.Random();

    private void Start()
    {
        FindTarget();
        _timer = _pause;
        _position = transform.position;
    }

    void FixedUpdate()
    {
        if (_getStrike == false)
        {
            _timer -= Time.fixedDeltaTime;
            if (_timer <= 0)
            {
                Move();
            }
            if (_position == _target)
            {
                _timer = _pause;
                FindTarget();
            }
        }
    }

    private void Move()
    {
        _position = transform.position;
        transform.position = Vector3.MoveTowards(_position, _target, _speed * Time.deltaTime);
    }

    private void FindTarget()
    {
        _position = transform.position;
        _men = GameObject.FindGameObjectsWithTag("Man");
        if (_men.Length > 0)
        {
            _man = _men[rnd.Next(_men.Length)];
            _target = _man.transform.position;
            transform.LookAt(_target);
        }

    }



}
