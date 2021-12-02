import sys
import pyqtgraph as pg
import pandas as pd
from PyQt5 import QtWidgets
import db
from matplotlib.backends.backend_qt5agg import FigureCanvasQTAgg, NavigationToolbar2QT
import matplotlib.pyplot as plt
import numpy as np
import lasio
from obspy.core import read as read_segy
from obspy.core.util import get_example_file
from obspy.core.trace import Trace
import prj_main_win
import read_segy_win
import json


def empty_path(self):
    msg = QtWidgets.QMessageBox()
    msg.information(self, "Внимание", "Пустой путь", QtWidgets.QMessageBox.Ok)

class prj_win(QtWidgets.QMainWindow, prj_main_win.Ui_prj_win):
    def __init__(self):
        super().__init__()
        self.setupUi(self)
        self.actionClose.triggered.connect(self.cls)
        self.actionWell_Map.triggered.connect(self.form_well_list)
        self.base_map = pg.PlotWidget()
        self.widget_base_map.addWidget(self.base_map)
        # self.base_map = plt.Figure()
        # self.ax = self.base_map.add_subplot()
        # self.canvas = FigureCanvasQTAgg(self.base_map)
        # self.widget_base_map.addWidget(self.canvas)
        self.actionRead_SEGY.triggered.connect(self.r_segy)

    def r_segy(self):
        path = self.sel_open_path()
        if path == '':
            empty_path(self)
        else:
            self.read_segy = reading_segy()
            # self.read_segy.setWindowModality(2)
            self.read_segy.show()
            self.read_segy.open_sgy(path)

    def post_base_map_profile(self, x, y):
        pr_dt = pg.PlotDataItem()
        x1 = np.array(x)
        y1 = np.array(y)
        pr_dt.setData(x1, y1)
        self.base_map.addItem(pr_dt)
        # self.ax.annotate(name, x[0], y[0], fontsize=5)
        # print(len(x), len(y))
        print(x)
        print(y)
        print('all good')


    def post_base_map_well(self, x, y, name):
        w = pg.ScatterPlotItem()
        w.setData(x, y)
        self.base_map.addItem(w)
        # self.base_map.subplots_adjust(top=0.97, bottom=0.05, left=0.04, right=0.985)
        # self.ax.scatter(x, y, s=6)
        # for i in range(len(x)):
        #     self.ax.annotate(name[i], (x[i], y[i]), fontsize=7)
        # self.canvas.draw()

    def form_well_list(self):
        well_data = self.read_well()
        w = self.tree_project.topLevelItem(0)
        for i in well_data['Well']:
            w.addChild(QtWidgets.QTreeWidgetItem([i]))
        self.post_base_map_well(well_data['X'], well_data['Y'], well_data['Well'])

    def sel_open_path(self):
        path = QtWidgets.QFileDialog.getOpenFileName()
        return path[0]

    def read_well(self):
        path = self.sel_open_path()
        if path != '':
            input_map = pd.read_csv(path, sep=' ')
            return input_map
        else:
            empty_path()

    def cls(self):
        self.close()

class reading_segy(QtWidgets.QMainWindow, read_segy_win.Ui_reading_segy):
    def __init__(self):
        super().__init__()
        self.setupUi(self)
        self.sld_trace.setTickInterval(1)
        self.sld_trace.setPageStep(1)
        self.fig_trace = plt.Figure()
        self.fig_seism = plt.Figure()
        self.fig_trace.gca().invert_yaxis()
        self.fig_seism.subplots_adjust(top=0.97, bottom=0.05, left=0.05, right=0.985)
        self.fig_trace.subplots_adjust(top=0.95, bottom=0.05)
        self.can_trace = FigureCanvasQTAgg(self.fig_trace)
        self.can_seism = FigureCanvasQTAgg(self.fig_seism)
        self.tools_fig_seism = NavigationToolbar2QT(self.can_seism, self)
        self.widg_trace.addWidget(self.can_trace)
        self.widg_seism.addWidget(self.can_seism)
        self.widg_seism.addWidget(self.tools_fig_seism)
        self.actionClose.triggered.connect(self.cls)
        self.sld_trace.valueChanged.connect(self.scroll_trace)
        self.txt_num_trace.returnPressed.connect(self.enter_num_trace)
        self.btn_x.clicked.connect(self.read_x)
        self.btn_y.clicked.connect(self.read_y)
        self.btn_trace.clicked.connect(self.read_trace)
        self.btn_cdp.clicked.connect(self.read_cdp)
        self.btn_read.clicked.connect(self.form_line)

    def cleaner_trace(self, L=False):
        if not L:
            L = self.widg_trace
        if L is not None:
            while L.count():
                item = L.takeAt(0)
                widget = item.widget()
                if widget is not None:
                    widget.deleteLater()
                else:
                    self.cleaner_trace(item.layout())

    def viz_trace(self, n):
        self.cleaner_trace()
        time = np.linspace(0, segy_file[0].stats.delta * 0.001 * len(segy_file.traces[0].data),
                           len(segy_file.traces[0].data))
        trace = segy_file.traces[n].data
        fig = plt.Figure()
        ax = fig.add_subplot()
        fig.subplots_adjust(top=0.95, bottom=0.05)
        ax.plot(trace, time, linewidth=0.25)
        ax.tick_params(labelsize=5)
        fig.gca().invert_yaxis()
        can = FigureCanvasQTAgg(fig)
        self.widg_trace.addWidget(can)

    def form_line(self):
        an = self.form_trace()
        x = self.form_x()
        y = self.form_y()
        p = prj_win()
        p.post_base_map_profile(x, y)

    def form_trace(self):
        trace = []
        for i in range(len(segy_file.traces)):
            tr = segy_file[i].stats.segy.trace_header[self.txt_x.text()]
            trace.append(tr)
        return trace

    def form_y(self):
        coord_y = []
        for i in range(len(segy_file.traces)):
            y = segy_file[i].stats.segy.trace_header[self.txt_y.text()]
            coord_y.append(y)
        return coord_y

    def form_x(self):
        coord_x = []
        for i in range(len(segy_file.traces)):
            x = segy_file[i].stats.segy.trace_header[self.txt_x.text()]
            coord_x.append(x)
        return coord_x

    def split_trace_tmp(self, s = str):
        s = s.split(sep='->')
        return s[0]

    def read_x(self):
        num = self.split_trace_tmp(self.lst_trace_header.currentItem().text())
        self.txt_x.setText(num)

    def read_y(self):
        num = self.split_trace_tmp(self.lst_trace_header.currentItem().text())
        self.txt_y.setText(num)

    def read_trace(self):
        num = self.split_trace_tmp(self.lst_trace_header.currentItem().text())
        self.txt_trace.setText(num)

    def read_cdp(self):
        num = self.split_trace_tmp(self.lst_trace_header.currentItem().text())
        self.txt_cdp.setText(num)

    def viz_segy(self):
        time = np.linspace(0, segy_file[0].stats.delta * 0.001 * len(segy_file.traces[0].data),
                           len(segy_file.traces[0].data))
        trace = segy_file.traces
        pr_amp = np.zeros(shape=(len(time), len(trace)))
        q = len(trace)
        for i in range(q):
            d = trace[i].data
            for j, k in enumerate(d):
                pr_amp[j][i] = int(k)
        ax = self.fig_seism.add_subplot()
        ax.contourf(pr_amp, levels= 32, cmap='seismic')
        self.fig_seism.gca().invert_yaxis()
        self.can_seism.draw()

    def scroll_trace(self):
        n = self.sld_trace.value()
        self.txt_num_trace.setText(str(n))
        self.read_trace_header(n)
        self.viz_trace(n)

    def enter_num_trace(self):
        n = int(self.txt_num_trace.text())
        self.sld_trace.setValue(n)
        self.sld_trace.setSliderPosition(n)
        self.read_trace_header(n)

    def read_trace_header(self, n):
        self.lst_trace_header.clear()
        rows = self.form_lst(n)
        self.lst_trace_header.addItems(rows)

    def form_lst(self, n):
        attr_name = list(segy_file[n].stats.segy.trace_header)
        lst = []
        tempory_lst = []
        for obj in segy_file[n].stats.segy.trace_header:
            tempory_lst.append(str(segy_file[n].stats.segy.trace_header[obj]))
        for i in range(0, len(attr_name)):
            lst.append(str(attr_name[i] + '->' + str(tempory_lst[i])))
        self.cmb_x.clear()
        self.cmb_x.addItems(attr_name)
        self.cmb_y.clear()
        self.cmb_y.addItems(attr_name)
        return lst

    def preview_trace(self):
        n = self.sld_trace.value()

    def open_sgy(self, path):
        global segy_file
        segy_file = read_segy(path, unpack_trace_headers=True)
        self.sld_trace.setMaximum(len(segy_file.traces) - 1)
        self.viz_segy()

    def cls(self):
        self.close()


def main():
    app = QtWidgets.QApplication(sys.argv)
    win = prj_win()
    win.show()
    sys.exit(app.exec_())

if __name__ == "__main__":
    main()



