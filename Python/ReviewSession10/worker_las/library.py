from sklearn.preprocessing import PolynomialFeatures as PF
import numpy as np
from sklearn import linear_model
from sklearn.pipeline import make_pipeline
import pickle
from pathlib import Path
from sklearn.model_selection import train_test_split
from scipy.stats import pearsonr as pirs


def poison_den(x):
    x1 = [1.545, 1.643, 1.741, 1.807, 1.840, 1.868, 1.877, 1.894, 1.905, 1.918, 1.932, 1.938, 1.953, 1.965, 1.983, 1.998,
          2.011, 2.027, 2.048, 2.065, 2.079, 2.097, 2.124, 2.151, 2.177, 2.213, 2.229, 2.249, 2.270, 2.302, 2.317, 2.331,
          2.352, 2.371, 2.381, 2.403, 2.428, 2.442, 2.460, 2.475, 2.489, 2.510, 2.531, 2.560, 2.592, 2.615, 2.657, 2.681,
          2.715, 2.758, 2.778, 2.807, 2.826, 2.854, 2.877, 2.900, 2.925, 2.957, 3.026, 3.062, 3.134, 3.187, 3.245, 3.289,
          3.344, 3.395, 3.436, 3.505]
    y1 = [0.493, 0.489, 0.483, 0.479, 0.475, 0.471, 0.470, 0.466, 0.463, 0.460, 0.456, 0.454, 0.450, 0.447, 0.441, 0.436,
         0.430, 0.422, 0.411, 0.403, 0.394, 0.384, 0.369, 0.354, 0.340, 0.321, 0.313, 0.303, 0.292, 0.278, 0.272, 0.266,
         0.259, 0.254, 0.251, 0.247, 0.241, 0.239, 0.236, 0.235, 0.234, 0.232, 0.229, 0.228, 0.228, 0.228, 0.230, 0.231,
         0.234, 0.238, 0.241, 0.245, 0.248, 0.250, 0.251, 0.253, 0.256, 0.257, 0.258, 0.259, 0.260, 0.260, 0.262, 0.262,
         0.262, 0.261, 0.262, 0.262]
    x1 = np.reshape(x1, (-1, 1))
    model = make_pipeline(PF(degree=9), linear_model.LinearRegression())
    model.fit(x1, y1)
    p = []
    p.append(x)
    z = model.predict(np.reshape(p, (-1, 1)))
    p.clear()
    return z

def den_vel(x_train, x_test, y_train, name):
    x_train = np.reshape(x_train, (-1, 1))
    x_test = np.reshape(x_test, (-1, 1))
    model = PF(degree=2)
    X_poly = model.fit_transform(x_train)
    x_test = model.fit_transform(x_test)
    pol_reg = linear_model.LinearRegression()
    pol_reg.fit(X_poly, y_train)
    z = pol_reg.predict(x_test)
    save_model(name, pol_reg)
    return z

def save_model(name, model):
    n = str(name).split(sep='.')
    file_name = n[0] +'.pkl'
    path = Path.cwd()/'moduls'/file_name
    with open(path, 'wb') as f:
        pickle.dump(model, f)

def load_model(name):
    file_name = name + '.pkl'
    path = Path.cwd()/'moduls'/file_name
    with open(path, 'rb') as f:
        l_model = pickle.load(f)
    return l_model

def apply_model(x_test, name):
    model = load_model(name)
    x_test = np.reshape(x_test, (-1, 1))
    tr = PF(degree=2)
    x_dt = tr.fit_transform(x_test)
    z = model.predict(x_dt)
    return z

def twin_differ_parm_to_corg(z):
    x = [3.266, 3.58, 3.819, 4.188, 5.367999999999999, 6.05, 6.9910000000000005, 7.747000000000001, 8.484, 9.204,
         10.015]
    y = [0.021, 1.054, 1.754, 3.045, 5.977, 7.489, 9.13, 10.402000000000001, 11.435, 12.412, 13.334000000000001]
    x = np.array(x)
    x = x / 10
    model = PF(degree=2)
    poly = model.fit_transform(np.reshape(x, (-1, 1)))
    pz = model.fit_transform(np.reshape(z, (-1, 1)))
    lin_reg = linear_model.LinearRegression()
    lin_reg.fit(poly, y)
    p = lin_reg.predict(pz)
    # print(lin_reg.intercept_)
    for i, j in enumerate(p):
        if j < 0:
            p[i] = 0
    return p
