<?php

namespace App\Http\Controllers;

use App\Http\Requests\TaskStatusRequest;
use App\Models\TaskStatus;
use Illuminate\Http\Request;
use Illuminate\Support\Arr;
use Illuminate\Support\Facades\Redirect;
use Illuminate\Support\Facades\Response;
use Illuminate\Support\Str;

class TaskStatusesController extends Controller
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
        $taskStatuses = TaskStatus::paginate(15);
        return  \response()->view('task_statuses.index', compact('taskStatuses'));
    }

    /**
     * Show the form for creating a new resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function create(): \Illuminate\Http\Response
    {
        $taskStatus = new TaskStatus();
        return response()
            ->view('task_statuses.create', compact('taskStatus'));
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param TaskStatusRequest $request
     * @return \Illuminate\Http\RedirectResponse
     */
    public function store(TaskStatusRequest $request): \Illuminate\Http\RedirectResponse
    {
        TaskStatus::create($request->validated());
        flash(__('Статус успешно создан'))->success();
        return redirect()->route('task_statuses.index');
    }


    /**
     * Show the form for editing the specified resource.
     *
     * @param  \App\Models\TaskStatus  $taskStatus
     * @return \Illuminate\Http\Response
     */
    public function edit(TaskStatus $taskStatus): \Illuminate\Http\Response
    {
        return \response()->view('task_statuses.edit', compact('taskStatus'));
    }

    /**
     * @param TaskStatusRequest $request
     * @param TaskStatus $taskStatus
     * @return \Illuminate\Http\RedirectResponse
     */
    public function update(TaskStatusRequest $request, TaskStatus $taskStatus)
    {
        $taskStatus->fill($request->validated());
        $taskStatus->save();
        flash(__('Статус успешно изменён'))->success();
        return redirect()->route('task_statuses.index');
    }

    /**
     * @param TaskStatus $taskStatus
     * @return \Illuminate\Http\RedirectResponse
     */
    public function destroy(TaskStatus $taskStatus): \Illuminate\Http\RedirectResponse
    {
        $tasksWithThisStatusCount = $taskStatus->tasks()->count();

        if ($tasksWithThisStatusCount === 0) {
            $taskStatus->delete();
            flash(__('Статус успешно удалён'))->success();
        } else {
            flash(__('Не удалось удалить статус'))->warning();
        }

        return redirect()->route('task_statuses.index');
    }
}
