using UnityEngine;
using UnityEngine.UI;

public class ButtonE : MonoBehaviour
{
    private static bool _getCue;
    public static bool GetCue
    {
        set { _getCue = value; }
    }

    private Image _buttonEImg;
    private GameObject _blackBall;

    private void Start()
    {
        _buttonEImg = GetComponent<Image>();
    }

    private void Update()
    { 
        HireButton();
    }

    private void HireButton()
    {
        Color colorButton = _buttonEImg.color;
        if (_getCue)
        {
            colorButton.a = 1;
        }
        else
        {
            colorButton.a = 0;
        }
        _buttonEImg.color = colorButton;
    }
}
