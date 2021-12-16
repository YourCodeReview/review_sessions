using UnityEngine;

public class Cue : MonoBehaviour
{
    private GameObject _blackBall;
    private Vector3 _centerRotation;
    private Vector3 _axisRotation = new Vector3(0, 1, 0);
    private Rigidbody _rigidCue;
    [SerializeField] float _speedRotaion;
    [SerializeField] float _impactForce;

    private void Start()
    {
        _blackBall = GameObject.FindGameObjectWithTag("BlackBall");
        _centerRotation = _blackBall.transform.position;
        _rigidCue = gameObject.GetComponent<Rigidbody>();
    }

    // Update is called once per frame
    void Update()
    {
        if (Input.GetKey(KeyCode.A))
        {
            RotateAroundBlackBall(true);
        }
        if (Input.GetKey(KeyCode.D))
        {
            RotateAroundBlackBall(false);
        }
        if (Input.GetKeyDown(KeyCode.Space))
        {
            HittingBall();
        }
    }

    private void RotateAroundBlackBall(bool direction)
    {
        if (direction)
        {
            transform.RotateAround(_centerRotation, _axisRotation, _speedRotaion * Time.deltaTime);
        }
        else
        {
            transform.RotateAround(_centerRotation, _axisRotation, -_speedRotaion * Time.deltaTime);
        }
    }

    private void HittingBall()
    {
        Vector3 direction = _blackBall.transform.position - transform.position;
        _rigidCue.AddForce(direction.normalized * _impactForce, ForceMode.Impulse);
    }
}
