using System;
using System.Collections.Generic;
using UnityEngine;

public class BlackBall : MonoBehaviour
{
    [SerializeField] float _power;
    private static List<GameObject> _listWhiteBall;
    [SerializeField] private float _limitSpeed;
    [SerializeField] float _pause;
    private Rigidbody _rigBalckBall;
    private float _minHieghtBall = -10;

    [SerializeField] GameObject _cue;
    private bool _haveCue;
    public bool HaveCue
    {
        get { return _haveCue;  }
    }

    [SerializeField] float _distanceCueY;
    [SerializeField] float _distanceCueZ;


    void Start()
    {
        _listWhiteBall = new List<GameObject>(GameObject.FindGameObjectsWithTag("WhiteBall"));
        _rigBalckBall = gameObject.GetComponent<Rigidbody>();
        GetCue();
    }

    void Update()
    {
        if (_rigBalckBall.velocity.x <= Math.Abs(_limitSpeed) && _rigBalckBall.velocity.y <= Math.Abs(_limitSpeed)
            && _rigBalckBall.velocity.z <= Math.Abs(_limitSpeed) && _haveCue == false)
        {
            ButtonE.GetCue = true;
            if (Input.GetKeyDown(KeyCode.E))
            {
                GetCue();
            }
        }
        else
        {
            ButtonE.GetCue = false;
        }
        if (transform.position.y <= _minHieghtBall)
        {
            Destroy(gameObject);
            Holes.ReturnBlackBall();
        }
    }

    public static void RemoveBallFromList(GameObject ball)
    {
        _listWhiteBall.Remove(ball);
    }

    public void GetCue()
    {
        Vector3 positionCue = transform.position;
        positionCue.z = transform.position.z - _distanceCueZ;
        positionCue.y = transform.position.y + _distanceCueY;
        GameObject cue =  GameObject.Instantiate<GameObject>(_cue);
        cue.transform.position = positionCue;
        _haveCue = true;
        ButtonE.GetCue = false;
    }

    private void OnCollisionEnter(Collision collision)
    {
        if (collision.gameObject.CompareTag("Cue"))
        {
            Destroy(collision.gameObject);
            _haveCue = false;
        }
    }
}
