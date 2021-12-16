using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class GUI : MonoBehaviour
{
    [SerializeField] GameObject _signCountLifeSM;
    private static List<GameObject> _listSignCountLife;
    private int _countLifeSuperMan;
    [SerializeField] float _stepImageLifeSign;
    [SerializeField] float _signLifeX;
    [SerializeField] float _signLifeY;
    [SerializeField] float _signLifeZ;

    [SerializeField] Text _outPutScore;

    private int _score = 0;
    public int Score
    {
        get { return _score; }
        set { _score = value; }
    }

    void Start()
    {
        _countLifeSuperMan = GameObject.FindGameObjectWithTag("SuperMan").GetComponent<SuperMan>().CountLife;
        for (int i = 0; i < _countLifeSuperMan; i++)
        {
            Vector3 location = new Vector3( _signLifeX, _signLifeY , _signLifeZ - (i + 1) * _stepImageLifeSign);
            GameObject imageSign = Instantiate(_signCountLifeSM);
            imageSign.transform.position = location;
        }
        _listSignCountLife = new List<GameObject>(GameObject.FindGameObjectsWithTag("SignLife"));
    }

    void Update()
    {
        _outPutScore.text = _score.ToString();
    }

    public static void ReducedNumberSignLife()
    {
        GameObject signLife = _listSignCountLife[_listSignCountLife.Count - 1];
        _listSignCountLife.Remove(signLife);
        Destroy(signLife.gameObject);
    }
}
