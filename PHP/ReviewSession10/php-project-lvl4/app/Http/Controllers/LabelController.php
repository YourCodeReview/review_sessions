<?php

namespace App\Http\Controllers;

use App\Models\Label;
use App\Http\Requests\LabelRequest;

class LabelController extends Controller
{

    public function __construct()
    {
        $this->middleware('auth')->except('index');
    }
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index(): \Illuminate\Http\Response
    {
        $labels = Label::paginate(15);
        return  response()->view('labels.index', compact('labels'));
    }

    /**
     * Show the form for creating a new resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function create(): \Illuminate\Http\Response
    {
        $label = new Label();
        return response()
            ->view('labels.create', compact('label'));
    }

    /**
     * Store a newly created resource in storage.
     * @param LabelRequest $request
     * @return \Illuminate\Http\RedirectResponse
     */
    public function store(LabelRequest $request): \Illuminate\Http\RedirectResponse
    {
        Label::create($request->input());
        flash(__('Метка успешно создана'))->success();
        return redirect()->route('labels.index');
    }


    /**
     * Show the form for editing the specified resource.
     *
     * @param  \App\Models\Label  $label
     * @return \Illuminate\Http\Response
     */
    public function edit(Label $label): \Illuminate\Http\Response
    {
        return \response()->view('labels.edit', compact('label'));
    }

    /**
     * @param LabelRequest $request
     * @param Label $label
     * @return \Illuminate\Http\RedirectResponse
     */
    public function update(LabelRequest $request, Label $label): \Illuminate\Http\RedirectResponse
    {
        $label->fill($request->input());
        $label->save();
        flash(__('Метка успешно изменена'))->success();
        return redirect()->route('labels.index');
    }

    /**
     * @param Label $label
     * @return \Illuminate\Http\RedirectResponse
     */
    public function destroy(Label $label): \Illuminate\Http\RedirectResponse
    {
        $tasksWithThisLabelCount = $label->tasks()->count();

        if ($tasksWithThisLabelCount === 0) {
            $label->delete();
            flash(__('Метка успешно удалена'))->success();
        } else {
            flash(__('Не удалось удалить метку'))->warning();
        }

        return redirect()->route('labels.index');
    }
}
