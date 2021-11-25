import matplotlib
from PyQt5 import Qt, QtGui, QtCore, QtWidgets
from matplotlib.backends.backend_qt5agg import FigureCanvasQTAgg, NavigationToolbar2QT
import matplotlib.ticker as ticker
import matplotlib.pyplot as plt
from pathlib import Path
import lib
import litoscan_win
import main_win
import lasio
import pandas as pd
import sys
import numpy as np
import seaborn as sns
from sklearn import preprocessing
from sklearn.linear_model import LinearRegression
from sklearn.svm import SVR
from sklearn.ensemble import RandomForestRegressor
from sklearn.model_selection import train_test_split
from sklearn.metrics import mean_squared_error
from scipy.stats import pearsonr as pirs
from scipy.signal import correlate as AFC
import pickle
import os
import library

matplotlib.use('Qt5Agg')
names_of_wggt_logs = []
names_of_wggt_tops = []

def empty_path(self):
    msg = QtWidgets.QMessageBox()
    msg.information(self, "Внимание", "Пустой путь", QtWidgets.QMessageBox.Ok)

def empty_name(self):
    msg = QtWidgets.QMessageBox()
    msg.information(self, 'Внимание', 'Не выбрано рабочее имя', QtWidgets.QMessageBox.Ok)

def empty_top_bot(self):
    msg = QtWidgets.QMessageBox()
    msg.information(self, "Внимание", "Не указана кровля или подошва", QtWidgets.QMessageBox.Ok)

def empty_param(self):
    msg = QtWidgets.QMessageBox()
    msg.information(self, "Внимание", "Не указан параметр", QtWidgets.QMessageBox.Ok)

def msg_curve_model(self):
    inp_name = QtWidgets.QInputDialog()
    text, ok = inp_name.getText(self, 'Введите имя кривой', 'Имя кривой')
    if ok:
        if text != '':
            return text
        else:
            empty_name(self)
            self.msg_name()

class prj_reg(QtWidgets.QMainWindow, main_win.Ui_MainWindow):
    def __init__(self):
        super().__init__()
        self.setupUi(self)
        self.formation_param_mlr()
        self.formation_svr()
        self.formation_rfr()
        self.actionClose.triggered.connect(self.cls)
        self.actionOpen_Las.triggered.connect(self.open_las)
        self.btn_25d_cross.clicked.connect(self.cross_2_5d)
        self.lst_keys.doubleClicked.connect(self.viz_logs)
        self.actionOpen_Tops.triggered.connect(self.form_tops)
        self.btn_clear_wdg.clicked.connect(self.clearvbox)
        self.actionOpen_Log.triggered.connect(self.open_log)
        self.actionPorosity.triggered.connect(self.calc_por)
        self.actionLitotype.triggered.connect(self.calc_lito_fac)
        self.dt_temp = pd.DataFrame()
        self.actionMin_Max.triggered.connect(self.min_max_param_log)
        self.actionIntensity.triggered.connect(self.double_param_log)
        self.actionGardner.triggered.connect(self.mlr_to_ak)
        self.scan_models()
        self.list_model.doubleClicked.connect(self.apply_model)
        self.actionDivision.triggered.connect(self.math_dev_las)
        self.actionDiv.triggered.connect(self.arif_div)
        self.actionAs_Log.triggered.connect(self.save_as_log)
        self.btn_viz_tops.clicked.connect(self.viz_tops)
        self.actionShift.triggered.connect(self.shift_data)
        self.btn_2d_cross.clicked.connect(self.cross_2d)
        self.btn_calc_mlr.clicked.connect(self.mlr_to_ak)
        self.btn_calc_svr.clicked.connect(self.svr_to_ak)
        self.btn_calc_rfr.clicked.connect(self.rfr_to_ak)
        self.actionModel.triggered.connect(self.save_model)
        self.actionMinus.triggered.connect(self.math_minus_las)
        self.btn_corr.clicked.connect(self.pair_cross)
        self.actionFFT.triggered.connect(self.fft_las)
        self.btn_rfr_test.clicked.connect(self.test_rfr_param_trees)
        self.btn_test_depth.clicked.connect(self.test_rfr_param_depth)
        self.btn_test_depth.setVisible(False)

    def test_rfr_param_depth(self):
        data_train, x_hlam, t, b = self.data_building()
        dt, dt_hlam = self.form_dt()
        p = float(self.txt_train_prop.text())
        x_train, x_test, y_train, y_test = train_test_split(data_train, dt[t:b], train_size=p)
        n = int(self.txt_n_trees.text())
        mist_depth = []
        pirs_depth = []
        for j in range(3, 100000, 200):
            test_depth = RandomForestRegressor(n_estimators=n, max_depth=j)
            test_depth.fit(x_train, y_train)
            d = test_depth.predict(x_test)
            train_m = test_depth.predict(x_train)
            train_mse = mean_squared_error(y_train, train_m)
            mse_depth = mean_squared_error(y_test, d)
            mist_depth.append(mse_depth)
            pirs_depth.append(train_mse)
        self.btn_rfr_test.setVisible(True)
        self.btn_test_depth.setVisible(False)
        itr_depth = np.linspace(3, 100000, 500)
        fig_depth = plt.Figure()
        ax = fig_depth.add_subplot()
        ax.xaxis.set_major_locator(ticker.MultipleLocator(200))
        ax.tick_params(labelsize=5)
        ax1 = ax.twinx()
        ax.plot(itr_depth, mist_depth, c='b', label='test')
        ax1.plot(itr_depth, pirs_depth, c='r', label='train')
        fig_depth.legend()
        can_depth = FigureCanvasQTAgg(fig_depth)
        self.layer_rfr_mist_depth.addWidget(can_depth)
        self.btn_test_depth.setVisible(False)

    def test_rfr_param_trees(self):
        data_train, x_hlam, t, b = self.data_building()
        dt, dt_hlam = self.form_dt()
        p = float(self.txt_train_prop.text())
        x_train, x_test, y_train, y_test = train_test_split(data_train, dt[t:b], train_size=p)
        mist_trees =[]
        mist_trees_test =[]
        pirs_trres = []
        for i in range(100, 2100, 100):
            test_trees = RandomForestRegressor(n_estimators=i)
            test_trees.fit(x_train, y_train)
            y_mse_train = test_trees.predict(x_train)
            mse_train = mean_squared_error(y_train, y_mse_train)
            t = test_trees.predict(x_test)
            mse_test = mean_squared_error(y_test, t)
            mist_trees.append(mse_test)
            pirs_trres.append(mse_train)
        itr_trees = np.linspace(100, 2000, 20)
        fig_trees = plt.Figure()
        ax = fig_trees.add_subplot()
        ax.xaxis.set_major_locator(ticker.MultipleLocator(200))
        ax.tick_params(labelsize=5)
        ax1 = ax.twinx()
        ax.plot(itr_trees, mist_trees, c='r', label='test')
        ax1.plot(itr_trees, pirs_trres, c='b', label='train')
        fig_trees.legend()
        can_trees = FigureCanvasQTAgg(fig_trees)
        self.layer_rfr_mist_tree.addWidget(can_trees)
        self.btn_rfr_test.setVisible(False)
        self.btn_test_depth.setVisible(True)

    def cleaner_curve2(self, L=False):
        if not L:
            L = self.layer_srav
        if L is not None:
            while L.count():
                item = L.takeAt(0)
                widget = item.widget()
                if widget is not None:
                    widget.deleteLater()
                else:
                    self.cleaner_curve2(item.layout())

    def form_srav_curve(self, predict_las):
        self.cleaner_curve2()
        ngk_name = self.comboBox_first_las.currentText()
        gk_name = self.comboBox_second_las.currentText()
        pr = dt_las[predict_las]
        ngk = dt_las[ngk_name]
        gk = dt_las[gk_name]
        d = dt_las.iloc[:, 0]
        s = {'NGK': ngk, 'GK': gk, 'Sint': pr}
        color_s = ['k', 'r', 'b']
        for i, j in enumerate(s):
            fig = plt.Figure(figsize=(5, 3))
            fig.subplots_adjust(top=0.97, bottom=0.04)
            ax = fig.add_subplot()
            ax.plot(s[j], d, c=color_s[i], linewidth = 0.5)
            ax.tick_params(labelsize=5, pad=-0.5)
            fig.gca().invert_yaxis()
            can = FigureCanvasQTAgg(fig)
            self.layer_srav.addWidget(can)

    def fft_las(self):
        las_name = self.lst_keys.currentItem().text()
        if self.txt_top_calc.text() == '' and self.txt_bot_calc.text() == '':
            las_curve = dt_las[las_name].to_numpy()
            d = dt_las.iloc[:, 0]
        else:
            t = int(self.txt_top_calc.text())
            b = int(self.txt_bot_calc.text())
            t = self.found_depth(t)
            b = self.found_depth(b)
            las_curve = dt_las[las_name][t:b].to_numpy()
            d = dt_las.iloc[t:b, 0]
        las_fft = np.fft.fft(las_curve)
        print(1)

    def viz_tops(self):
        las_name = self.lst_keys.currentItem().text()
        las_data = dt_las[las_name]
        x_min = np.nanmin(las_data)
        x_max = np.nanmax(las_data)
        names = dt_tops['Tops'].tolist()
        d = dt_tops['MD'].tolist()
        for i, j in enumerate(names):
            ax.annotate(j, (x_max -0.1 * x_max, int(d[i])), color = 'r', fontsize=5.5)
            ax.hlines(int(d[i]), x_min, x_max, linewidth=0.5, color='g')

    def cleaner_cross(self, L=False):
        if not L:
            L = self.widg_cross
        if L is not None:
            while L.count():
                item = L.takeAt(0)
                widget = item.widget()
                if widget is not None:
                    widget.deleteLater()
                else:
                    self.clear_dist(item.layout())

    def clearner_glist(self, L=False):
        if not L:
            L = self.widg_gist
        if L is not None:
            while L.count():
                item = L.takeAt(0)
                widget = item.widget()
                if widget is not None:
                    widget.deleteLater()
                else:
                    self.clear_dist(item.layout())

    def form_twin_plot(self, f, p):
        plt.close()
        self.cleaner_svod_curves()
        self.clearner_glist()
        self.cleaner_cross()
        depth = dt_las.iloc[:, 0]
        m = f - p
        svod = {'Fit': f, 'Predict': p, 'Mistake': m}
        svod = pd.DataFrame(svod, index=None)
        for i in svod.columns:
            fig = plt.Figure(figsize=(5, 3))
            ax = fig.add_subplot()
            fig.subplots_adjust(top=0.97)
            ax.plot(svod[i], depth, linewidth = 0.25)
            ax.tick_params(labelsize=5, which='both', pad=1, length=2)
            fig.gca().invert_yaxis()
            can = FigureCanvasQTAgg(fig)
            self.layer_stat.addWidget(can)
        g = sns.distplot(m, bins=100).get_figure()
        glist = FigureCanvasQTAgg(g)
        cross = plt.Figure()
        ax_cross = cross.add_subplot()
        ax_cross.scatter(f, p, s=3)
        can_cross = FigureCanvasQTAgg(cross)
        self.widg_gist.addWidget(glist)
        self.widg_cross.addWidget(can_cross)

    def pair_cross(self):
        self.clearvbox()
        if self.txt_top_cross.text() == '' and self.txt_bot_cross.text() == '':
            df = pd.DataFrame((dt_las[self.axix_x.currentText()], dt_las[self.axix_y.currentText()]),
                              {self.axix_x.currentText(), self.axix_y.currentText()})
            p = sns.PairGrid(df)
        # else:
        #     x, y, z = self.form_int_data()
        #     p = sns.pairplot((x, y))
        c = FigureCanvasQTAgg(p.map(sns.scatterplot))
        self.widg_cross.addWidget(c)
        print(1)

    def afc_graf(self, l1, l2):
        depth = dt_las.iloc[:, 0]
        afc = AFC(l1, l2)
        fig = plt.Figure()
        ax = fig.add_subplot()
        ax.plot(afc)
        can = FigureCanvasQTAgg(fig)
        self.layer_afc.addWidget(can)

    def math_minus_las(self):
        l1 = self.comboBox_first_las.currentText()
        l2 = self.comboBox_second_las.currentText()
        las1 = dt_las[l1] - dt_las[l2]
        name = l1 + '-' + l2
        dt_las[name] = las1
        self.form_boxs()
        self.form_lst()
        self.form_data_table()

    def formation_svr(self):
        param_list = ['linear', 'poly', 'rbf', 'sigmoid', 'precomputed']
        param_value = QtWidgets.QComboBox()
        param_value.addItems(param_list)
        self.tbl_param_svr.setCellWidget(0, 0, param_value)

    def formation_param_mlr(self):
        for i in range(self.tbl_mlr_param.rowCount()):
            param_value = QtWidgets.QComboBox()
            param_value.addItems(['False', 'True'])
            self.tbl_mlr_param.setCellWidget(i, 0, param_value)

    def formation_rfr(self):
        param_list = ["squared_error", "absolute_error", "poisson"]
        param_value = QtWidgets.QComboBox()
        param_value.addItems(param_list)
        self.tbl_param_rfr.setCellWidget(1, 0, param_value)

    def shift_data(self):
        name = self.cmb_inp_calc.currentText()
        x = np.array(dt_las[name].tolist())
        t = self.found_depth(float(self.txt_top_calc.text()))
        b = self.found_depth(float(self.txt_bot_calc.text()))
        delta = abs(x[:t].min() - np.log(x[t:b]).min())
        for i in range(t, b+1):
            x[i] = np.log(x[i]) - delta
        dt_las[name + '_shift'] = x
        self.form_lst()
        self.form_boxs()
        self.form_data_table()

    def poison_from_den(self):
        x = dt_las[self.cmb_inp_calc.currentText()].tolist()
        x = np.reshape(x, (-1, 1))
        poison_sint = library.poison_den(x)
        dt_las['Poison'] = poison_sint
        self.form_boxs()
        self.form_lst()

    def sel_path_to_save(self):
        path = QtWidgets.QFileDialog.getSaveFileName(self, 'Save Log', self.lbl_well_name.text())
        if path[0] != '':
            return path[0]
        else:
            empty_path(self)

    def save_as_log(self):
        # name = self.lst_keys.currentItem().text()
        path = self.sel_path_to_save()
        dt_las.to_csv(path, index=None, sep='\t')

    def arif_div(self):
        n = float(self.txt_scalar.text())
        dt_name = self.cmb_inp_calc.currentText()
        if n != '':
            if self.txt_top_calc.text() == '' and self.txt_bot_calc.text() == '':
                dt = dt_las[dt_name]
                new_name = dt_name + '/' + n
                dt_las[new_name] = dt / n
            else:
                t = self.found_depth(float(self.txt_top_calc.text()))
                b = self.found_depth(float(self.txt_bot_calc.text()))
                for i in range(t, b + 1):
                    dt_las.loc[i, dt_name] = dt_las[dt_name][i] / n
        else:
            empty_param(self)
        self.form_lst()
        self.form_boxs()
        self.form_data_table()

    def math_dev_las(self):
        l1 = self.comboBox_first_las.currentText()
        l2 = self.comboBox_second_las.currentText()
        las1 = dt_las[l1] / dt_las[l2]
        name = l1 + '/' + l2
        dt_las[name] = las1
        self.form_boxs()
        self.form_lst()
        self.form_data_table()

    def apply_model(self):
        name = self.list_model.currentItem().text()
        model = self.load_model(name)
        x_svod = self.form_x_svod()
        sint = model.predict(x_svod)
        dt_las['Sint'] = sint
        self.viz_curve(dt_las.iloc[:, 0], sint)
        self.form_boxs()
        self.form_lst()
        self.form_data_table()

    def load_model(self, name):
        file_name = name + '.pkl'
        path = Path.cwd() / 'moduls' / file_name
        with open(path, 'rb') as f:
            l_model = pickle.load(f)
        return l_model

    def cleaner_svod_curves(self, L=False):
        if not L:
            L = self.layer_stat
        if L is not None:
            while L.count():
                item = L.takeAt(0)
                widget = item.widget()
                if widget is not None:
                    widget.deleteLater()
                else:
                    self.cleaner_svod_curves(item.layout())

    def scan_models(self):
        self.list_model.clear()
        path = Path.cwd()/'moduls'
        models = []
        for file in os.listdir(path):
            if file.endswith(".pkl"):
                models.append(file)
        for i in models:
            t = str(i).split(sep='.')
            self.list_model.addItem(t[0])

    def msg_name_model(self):
        inp_name = QtWidgets.QInputDialog()
        text, ok = inp_name.getText(self, 'Введите имя модели', 'Название модели')
        if ok:
            if text != '':
                return text
            else:
                empty_name(self)
                self.msg_name()

    def save_model(self):
        model = eq
        name = self.msg_name_model()
        file_name = name + '.pkl'
        path = Path.cwd() / 'moduls' / file_name
        with open(path, 'wb') as f:
            pickle.dump(model, f)
        self.scan_models()

    def data_building(self):
        t = int(self.txt_top_calc.text())
        b = int(self.txt_bot_calc.text())
        t = self.found_depth(t)
        b = self.found_depth(b)
        ngk = np.array(dt_las[self.comboBox_first_las.currentText()])
        ngk = np.reshape(ngk, (-1, 1))
        gk = np.array(dt_las[self.comboBox_second_las.currentText()])
        gk = np.reshape(gk, (-1, 1))
        k_por = preprocessing.MinMaxScaler().fit_transform(ngk)
        k_clay = preprocessing.MinMaxScaler().fit_transform(gk)
        k_skelet = 1 - k_por - k_clay
        k_skelet = np.array(k_skelet)
        x_train = np.column_stack((k_por[t:b], k_clay[t:b], k_skelet[t:b]))
        x_test = np.column_stack((k_por, k_clay, k_skelet))
        x_test = pd.DataFrame(x_test, columns={'Kpor', 'Kclay', 'Kskel'})
        # x_test['Depth'] = dt_las.iloc[:, 0]
        x_test = x_test.fillna(np.nanmedian(x_test))
        return x_train, x_test, t, b

    def form_param_mlr(self):
        param = []
        for i in range(self.tbl_mlr_param.rowCount()):
            w = self.tbl_mlr_param.cellWidget(i, 0)
            param.append(w.currentText())
        return param

    def form_param_svr(self):
        param = []
        param.append(self.tbl_param_svr.cellWidget(0, 0).currentText())
        for i in range(1, self.tbl_param_svr.rowCount()):
            w = self.tbl_param_svr.item(i, 0).text()
            param.append(w)
        return param

    def form_param_rfr(self):
        param = []
        param.append(int(self.tbl_param_rfr.item(0, 0).text()))
        param.append(self.tbl_param_rfr.cellWidget(1, 0).currentText())
        for i in range(2, self.tbl_param_rfr.rowCount()):
            w = self.tbl_param_rfr.item(i, 0).text()
            param.append(w)
        return param

    def form_dt(self):
        dt = dt_las[self.comboBox_teach.currentText()]
        dt_test = dt
        dt = dt.fillna(np.median(dt))
        dt = np.array(dt)
        return dt, dt_test

    def mlr_to_ak(self):
        if self.txt_top_calc.text() != '' and self.txt_bot_calc.text() != '':
            dt, dt_test = self.form_dt()
            x_train, x_test, t, b = self.data_building()
            global eq
            eq = LinearRegression(fit_intercept=False)
            eq.fit(x_train, dt[t:b])
            dt_sint = eq.predict(x_test)
            new_name = msg_curve_model(self)
            dt_las[new_name] = dt_sint
            self.form_twin_stat(dt_test, dt_sint, eq)
            self.form_srav_curve(new_name)
            self.form_lst()
            self.form_boxs()
            self.form_data_table()
            self.form_twin_plot(dt, dt_sint)

    def svr_to_ak(self):
        if self.txt_top_calc.text() != '' and self.txt_bot_calc.text() != '':
            dt, dt_test = self.form_dt()
            x_train, x_test, t, b = self.data_building()
            param = self.form_param_svr()
            global eq
            eq = SVR(kernel=param[0])
            eq.fit(x_train, dt[t:b])
            dt_sint = eq.predict(x_test)
            new_name = msg_curve_model(self)
            dt_las[new_name] = dt_sint
            self.form_twin_stat(dt_test, dt_sint, eq)
            self.form_boxs()
            self.form_lst()
            self.form_data_table()

    def rfr_to_ak(self):
        if self.txt_top_calc.text() != '' and self.txt_bot_calc.text() != '':
            dt, dt_test = self.form_dt()
            x_train, x_test, t, b = self.data_building()
            param = self.form_param_rfr()
            global eq
            eq = RandomForestRegressor(n_estimators=int(param[0]), max_depth=int(param[2]),
                                       min_samples_split=int(param[3]), min_samples_leaf=int(param[4]),
                                       max_features=int(param[5]), max_samples=int(param[6]))
            eq.fit(x_train, dt[t:b])
            dt_sint = eq.predict(x_test)
            new_name = msg_curve_model(self)
            dt_las[new_name] = dt_sint
            self.form_srav_curve(new_name)
            self.form_twin_stat_for_frorest(dt_test, dt_sint, eq)
            self.form_boxs()
            self.form_lst()
            self.form_data_table()
            self.form_twin_plot(dt_test, dt_sint)

    def form_twin_stat(self, x, y, model):
        x = x.fillna(np.nanmedian(x))
        self.list_stat_model.clear()
        p = pirs(x, y)
        self.list_stat_model.addItem(str(p[0]))
        coef = model.coef_
        for i, j in enumerate(coef):
            self.list_stat_model.addItem('Coef #' + str(i) + '=' + str(j))

    def form_twin_stat_for_frorest(self, x, y, model):
        x = x.fillna(np.nanmedian(x))
        self.list_stat_model.clear()
        p = pirs(x, y)
        self.list_stat_model.addItem(str(p[0]))

    def zero_scan(self, x):
        x = x.fillna(0)
        if x[0] != 0:
            t = 0
            b = 0
            for i, z in enumerate(x):
                if i < len(x)-1:
                    if z != '0' and t == 0:
                        t = i + 1
                    if z != '0' and x[i+1] == '0':
                        b = i + 1
            self.txt_top_calc.setText(str(t))
            if b == 0:
                self.txt_bot_calc.setText(str(len(x)))
            else:
                self.txt_bot_calc.setText(str(b))
        return t, b

    def msg_min(self):
        inp_name = QtWidgets.QInputDialog()
        text, ok = inp_name.getText(self, 'Введите значение минимума', 'Значение минимума')
        if ok:
            if text != '':
                return text
            else:
                empty_name(self)
                self.msg_min()

    def double_param_log(self):
        if self.txt_top_calc.text() != '' and self.txt_bot_calc.text() != '':
            t = self.found_depth(int(self.txt_top_calc.text()))
            b = self.found_depth(int(self.txt_bot_calc.text()))
            las = dt_las[self.cmb_inp_calc.currentText()][t:b].tolist()
            log = dt_las[self.cmb_inp_calc.currentText()].tolist()
            baseline = max(las)
            m = min(log)
            if m == 0:
                m = int(self.msg_min())
            twp = []
            for i in log:
                z = (i - m) / (baseline - m)
                twp.append(z)
            name_las = self.msg_name()
            dt_las[name_las] = twp
            self.form_data_table()
            self.form_boxs()
            self.form_lst()
        else:
            empty_top_bot(self)

    def min_max_param_log(self):
        m = float(self.msg_min())
        name = self.cmb_inp_calc.currentText()
        dt_las[name] = dt_las[name].fillna(m)
        log_data = np.array(dt_las[name])
        log_data = np.reshape(log_data, (-1, 1))
        scal = preprocessing.MinMaxScaler()
        d_log =scal.fit_transform(log_data)
        name_las = self.msg_name()
        dt_las[name_las] = d_log
        self.form_data_table()
        self.form_boxs()
        self.form_lst()

    def msg_name(self):
        inp_name = QtWidgets.QInputDialog()
        text, ok = inp_name.getText(self, 'Введите имя', 'Название кривой')
        if ok:
            if text != '':
                return text
            else:
                empty_name(self)
                self.msg_name()

    def clear_dist(self, L=False):
        if not L:
            L = self.dist_log
        if L is not None:
            while L.count():
                item = L.takeAt(0)
                widget = item.widget()
                if widget is not None:
                    widget.deleteLater()
                else:
                    self.clear_dist(item.layout())

    def form_stat_block(self, x):
        self.lst_stat_las.clear()
        m1 = min(x)
        m2 = max(x)
        self.clear_dist()
        cn = sns.distplot(x, bins=200).get_figure()
        can = FigureCanvasQTAgg(cn)
        self.dist_log.addWidget(can)
        self.lst_stat_las.addItem('Минимальное значение: ' + str(m1))
        self.lst_stat_las.addItem('Максимальное значение: ' + str(m2))
        self.lst_stat_las.addItem('50%: ' + str(np.nanmedian(x)))
        self.lst_stat_las.addItem('90%: ' + str(np.nanquantile(x, q=0.9)))

    def calc_lito_fac(self):
        self.calc_lf = lito_las()
        self.calc_lf.cmb_NGK.clear()
        self.calc_lf.cmb_GK.clear()
        self.calc_lf.cmb_PS.clear()
        self.calc_lf.cmb_NGK.addItems(dt_las.columns)
        self.calc_lf.cmb_GK.addItems(dt_las.columns)
        self.calc_lf.cmb_PS.addItems(dt_las.columns)
        self.calc_lf.db_las = dt_las
        self.calc_lf.show()

    def open_log(self):
        self.lst_keys.clear()
        path = self.sel_path_to_file()
        if path != '':
            global dt_las
            dt_las = pd.read_csv(path, sep='\t', na_values=['-1.0000', '0.0'], encoding='ANSI')
            # dt_las = dt_las.fillna(0)
            self.lst_keys.addItems(dt_las.columns)
            self.name_well_print(path)
            self.form_data_table()
            self.form_boxs()
        else:
            empty_path(self)

    def form_lst(self):
        self.lst_keys.clear()
        self.lst_keys.addItems(dt_las.columns)

    def clearvbox(self, L=False):
        if not L:
            L = self.wdgt_cross
        if L is not None:
            while L.count():
                item = L.takeAt(0)
                widget = item.widget()
                if widget is not None:
                    widget.deleteLater()
                else:
                    self.clearvbox(item.layout())

    def form_tops(self):
        path = self.sel_path_to_file()
        if path != '':
            global dt_tops
            dt_tops = pd.read_csv(path, sep='\t')
            dt_tops = dt_tops.transpose()
            dt_tops.reset_index(inplace=True)
            dt_tops = dt_tops.drop(index=0)
            dt_tops = dt_tops.rename(columns={'index': 'Tops', 0:'MD'})
            self.form_tops_table()
        else:
            empty_path(self)

    def form_tops_table(self):
        self.tbl_tops.clear()
        self.tbl_tops.setRowCount(len(dt_tops.index))
        self.tbl_tops.setColumnCount(len(dt_tops.columns))
        self.tbl_tops.setHorizontalHeaderLabels(dt_tops.columns)
        for p, i in enumerate(dt_tops.columns):
            col = dt_tops[i]
            for j, k in enumerate(col):
                zn = QtWidgets.QTableWidgetItem(str(k))
                self.tbl_tops.setItem(j, p, zn)
        self.tbl_tops.resizeColumnsToContents()

    def cleaner_log(self, L=False):
        if not L:
            L = self.wdgt_log
        if L is not None:
            while L.count():
                item = L.takeAt(0)
                widget = item.widget()
                if widget is not None:
                    widget.deleteLater()
                else:
                    self.clearvbox(item.layout())
        # for i in names_of_wggt_logs:
        #     self.wdgt_log.removeWidget(i)
        # names_of_wggt_logs.clear()

    def viz_curve(self, depth, log):
        plt.close()
        self.cleaner_log()
        x_min = min(log)
        x_max = max(log)
        if self.wdgt_log.count() > 0:
            self.cleaner_log()
        global fig_las
        fig_las = plt.Figure()
        fig_las.subplots_adjust(right=0.5, top=0.975, bottom=0.04)
        global canvas_las
        canvas_las = FigureCanvasQTAgg(fig_las)
        global ax
        ax = fig_las.add_subplot()
        ax.plot(log, depth, linewidth=0.25)
        ax.tick_params(axis='both', which='major', labelsize=7, labelcolor='r')
        fig_las.gca().invert_yaxis()
        toolbar = NavigationToolbar2QT(canvas_las, self)
        self.wdgt_log.addWidget(canvas_las)
        self.wdgt_log.addWidget(toolbar)
        canvas_las.draw()

    def viz_logs(self):
        name = self.lst_keys.item(0).text()
        depth = dt_las[name]
        log_name = self.lst_keys.currentItem().text()
        log_curve = dt_las[log_name]
        self.viz_curve(depth, log_curve)
        self.form_stat_block(log_curve)
        if self.tbl_tops.rowCount() != 0:
            self.viz_tops()

    def found_depth(self, y):
        x = list(map(float, dt_las.iloc[:, 0]))
        x = np.array(x)
        delt = abs(x - y)
        f = np.argmin(delt)
        return f

    def calc_por(self):
        if self.txt_top_calc.text() != '' and self.txt_bot_calc.text() != '':
            t = int(self.txt_top_calc.text())
            b = int(self.txt_bot_calc.text())
            t = self.found_depth(t)
            b = self.found_depth(b)
            x = dt_las[self.cmb_inp_calc.currentText()][t:b]
            por = np.exp(1 / x)
            self.form_frame('Porosity', t, b, por)
        else:
            x = dt_las[self.cmb_inp_calc.currentText()]
            por = np.exp(1/x)
            dt_las['Porosity'] = por
        self.lst_keys.clear()
        self.lst_keys.addItems(dt_las.columns)
        self.form_boxs()

    def form_frame(self, name, t, b, x):
        dt_las[name][:t] = None
        dt_las[name][t:b] = x
        dt_las[name][b:] = None

    def form_int_data(self):
        t = int(self.txt_top_cross.text())
        t = self.found_depth(t)
        b = int(self.txt_bot_cross.text())
        b = self.found_depth(b)
        x = dt_las[self.axix_x.currentText()][t:b]
        y = dt_las[self.axix_y.currentText()][t:b]
        z = dt_las[self.axix_z.currentText()][t:b]
        return x, y, z

    def cross_2d(self):
        self.clearvbox()
        global fig_cross
        fig_cross = plt.Figure()
        ax = fig_cross.add_subplot()
        if self.txt_top_cross.text() == '' and self.txt_bot_cross.text() == '':
            ax.scatter(dt_las[self.axix_x.currentText()], dt_las[self.axix_y.currentText()], s=3)
        else:
            x, y, z = self.form_int_data()
            ax.scatter(x, y, s=3)
        global canvas_cross
        canvas_cross = FigureCanvasQTAgg(fig_cross)
        toolbar_cross = NavigationToolbar2QT(canvas_cross, self)
        self.wdgt_cross.addWidget(canvas_cross)
        self.wdgt_cross.addWidget(toolbar_cross)

    def cross_2_5d(self):
        self.clearvbox()
        global fig_cross
        fig_cross = plt.Figure()
        ax = fig_cross.add_subplot()
        if self.txt_top_cross.text() == '' and self.txt_bot_cross.text() == '':
            ax.scatter(dt_las[self.axix_x.currentText()], dt_las[self.axix_y.currentText()],
                   c=dt_las[self.axix_z.currentText()], cmap='jet', s=3)
        else:
            x, y, z = self.form_int_data()
            ax.scatter(x, y, c=z, cmap='jet', s=3)
            self.label_Kk.setText(str(pirs(x, y)[0]))
        global canvas_cross
        canvas_cross = FigureCanvasQTAgg(fig_cross)
        toolbar_cross = NavigationToolbar2QT(canvas_cross, self)
        self.wdgt_cross.addWidget(canvas_cross)
        self.wdgt_cross.addWidget(toolbar_cross)

    def codisp(self, x):
        self.cleardisp()
        fig = sns.distplot(x).get_figure()
        can = FigureCanvasQTAgg(fig)
        self.dist_log.addWidget(can)

    def cleardisp(self, L=False):
        if not L:
            L = self.dist_log
        if L is not None:
            while L.count():
                item = L.takeAt(0)
                widget = item.widget()
                if widget is not None:
                    widget.deleteLater()
                else:
                    self.cleardisp(item.layout())

    def sel_path_to_file(self):
        path = QtWidgets.QFileDialog.getOpenFileName()
        return path[0]

    def open_las(self):
        self.lst_keys.clear()
        path = self.sel_path_to_file()
        if path != '':
            curve = lasio.read(path)
            global dt_las
            dt_las = pd.DataFrame(curve.df(), index=None)
            # dt_las = dt_las.fillna(0)
            dt_las.reset_index(inplace=True)
            self.lst_keys.addItems(dt_las.columns)
            self.name_well_print(path)
            self.form_data_table()
            self.form_boxs()
        else:
            empty_path(self)

    def form_boxs(self):
        self.axix_x.clear()
        self.axix_y.clear()
        self.axix_z.clear()
        self.cmb_inp_calc.clear()
        self.comboBox_first_las.clear()
        self.comboBox_second_las.clear()
        self.comboBox_teach.clear()
        self.axix_x.addItems(dt_las.columns)
        self.axix_y.addItems(dt_las.columns)
        self.axix_z.addItems(dt_las.columns)
        self.cmb_inp_calc.addItems(dt_las.columns)
        self.comboBox_first_las.addItems(dt_las.columns)
        self.comboBox_second_las.addItems(dt_las.columns)
        self.comboBox_teach.addItems(dt_las.columns)

    def name_well_print(self, p: str):
        file = p.split(sep= '/')
        file = file[len(file) - 1]
        name = file.split(sep= '.')
        self.lbl_well_name.setText(name[0])
        self.lbl_well_name.adjustSize()

    def form_data_table(self):
        self.tbl_well_data.clear()
        self.tbl_well_data.setRowCount(len(dt_las.index))
        self.tbl_well_data.setColumnCount(len(dt_las.columns))
        self.tbl_well_data.setHorizontalHeaderLabels(dt_las.columns)
        for p, i in enumerate(dt_las.columns):
            col = dt_las[i]
            for j, k in enumerate(col):
                zn = QtWidgets.QTableWidgetItem(str(k))
                self.tbl_well_data.setItem(j, p, zn)
        self.tbl_well_data.resizeColumnsToContents()

    def cls(self):
        self.close()

class lito_las(QtWidgets.QMainWindow, litoscan_win.Ui_MainWindow):
    def __init__(self):
        super().__init__()
        self.setupUi(self)
        self.btn_calc.clicked.connect(self.calc_lit)
        self.db_las = pd.DataFrame()

    def calc_lit(self):
        ngk = self.db_las[self.cmb_NGK.currentText()]
        gk = self.db_las[self.cmb_GK.currentText()]
        ps = self.db_las[self.cmb_PS.currentText()]
        lf = lib.lito_model(ngk, gk, ps)
        clf = lf.lito_write()
        p = prj_reg()
        p.dt_temp['Litotype'] = clf
        self.close()

def main():
    app = QtWidgets.QApplication(sys.argv)
    window = prj_reg()
    window.show()
    sys.exit(app.exec_())

if __name__ == "__main__":
    main()
