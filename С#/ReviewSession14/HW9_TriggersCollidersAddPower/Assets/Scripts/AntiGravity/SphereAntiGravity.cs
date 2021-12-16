using UnityEngine;

public class SphereAntiGravity : MonoBehaviour
{
    [SerializeField] private float _maxValueX;
    private float _minValueX;
    [SerializeField] private float _maxValueY;
    private float _minValueY;
    [SerializeField] private float _sppedMove;


    // Start is called before the first frame update
    void Start()
    {
        _minValueX = -_maxValueX;
        _minValueY = -_maxValueY;
    }

    // Update is called once per frame
    void Update()
    {
        MoveObject();
    }

    public void ReturnToStart()
    {
        gameObject.transform.position = Vector3.zero;
    }

    private void OnTriggerEnter(Collider other)
    {
        other.GetComponent<Rigidbody>().useGravity = false;
    }

    private void OnTriggerExit(Collider other)
    {
        other.GetComponent<Rigidbody>().useGravity = true;
    }

    private void MoveObject()
    {
        if (Input.GetKey(KeyCode.A) && transform.position.x >= _minValueX)
        {
            Vector3 pos = transform.position;
            pos.x -= _sppedMove * Time.deltaTime;
            transform.position = pos;
        }
        if (Input.GetKey(KeyCode.D) && transform.position.x <= _maxValueX)
        {
            Vector3 pos = transform.position;
            pos.x += _sppedMove * Time.deltaTime;
            transform.position = pos;
        }
        if (Input.GetKey(KeyCode.W) && transform.position.y <= _maxValueY)
        {
            Vector3 pos = transform.position;
            pos.y += _sppedMove * Time.deltaTime;
            transform.position = pos;
        }
        if (Input.GetKey(KeyCode.S) && transform.position.x >= _minValueY)
        {
            Vector3 pos = transform.position;
            pos.y -= _sppedMove * Time.deltaTime;
            transform.position = pos;
        }
        if (Input.GetKey(KeyCode.E))
        {
            Vector3 pos = transform.position;
            pos.z += _sppedMove * Time.deltaTime;
            transform.position = pos;
        }
        if (Input.GetKey(KeyCode.Q))
        {
            Vector3 pos = transform.position;
            pos.z -= _sppedMove * Time.deltaTime;
            transform.position = pos;
        }
    }
}
