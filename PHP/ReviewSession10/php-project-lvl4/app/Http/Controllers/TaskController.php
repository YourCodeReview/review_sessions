<?php

namespace App\Http\Controllers;

use App\Models\Label;
use App\Models\Task;
use App\Http\Requests\TaskRequest;
use App\Models\TaskStatus;
use App\Models\User;
use Auth;
use Illuminate\Http\RedirectResponse;
use Illuminate\Http\Request;
use Illuminate\Http\Response;
use Illuminate\Support\Facades\Gate;
use Spatie\QueryBuilder\QueryBuilder;
use Symfony\Component\HttpKernel\Exception\AccessDeniedHttpException;
use Spatie\QueryBuilder\AllowedFilter;

use function redirect;
use function response;

class TaskController extends Controller
{

    public function __construct()
    {
        $this->middleware('auth')->only(['create', 'update', 'store','edit','destroy']);
    }

    /**
     * Display a listing of the resource.
     *
     * @param Request $request
     * @return Response
     */
    public function index(Request $request): Response
    {
        $tasks = QueryBuilder::for(Task::class)
            ->allowedFilters(
                [
                    AllowedFilter::exact('status_id'),
                    AllowedFilter::exact('created_by_id'),
                    AllowedFilter::exact('assigned_to_id'),

                ]
            )
            ->orderBy('created_at')
            ->get()
            ->all();

        $filterParams = $request->input('filter');

        $statusId = $filterParams['status_id'] ?? '';
        $createdById = $filterParams['created_by_id'] ?? '';
        $assignedToId = $filterParams['assigned_to_id'] ?? '';

        $usersList = User::pluck('name', 'id')->all();
        $statusesList = TaskStatus::pluck('name', 'id')->all();

        return response()->view(
            'tasks.index',
            compact(
                'tasks',
                'usersList',
                'statusesList',
                'statusId',
                'createdById',
                'assignedToId'
            )
        );
    }

    /**
     * Display a listing of the resource.
     *
     * @param Task $task
     * @return Response
     */
    public function show(Task $task): Response
    {
        return response()->view('tasks.show', compact('task'));
    }

    /**
     * Show the form for creating a new resource.
     *
     * @return Response
     */
    public function create(): Response
    {
        $task = new Task();
        $statusesList = ['' => 'Выберите статус'] + TaskStatus::pluck('name', 'id')->all();
        $usersList = ['' => 'Выберите исполнителя'] + User::pluck('name', 'id')->all();
        $labelsList = ['' => 'Добавьте метки'] + Label::pluck('name', 'id')->all();

        return response()
            ->view('tasks.create', compact('task', 'statusesList', 'usersList', 'labelsList'));
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  TaskRequest  $request
     * @return RedirectResponse
     */
    public function store(TaskRequest $request): RedirectResponse
    {
        $data = $request->input() + ['created_by_id' => Auth::id()];

        $task = Task::create($data);

        if (isset($data['labels'])) {
            $labelsIds = array_filter($data['labels']);
            $labels = Label::whereIn('id', $labelsIds)->get()->all();
            $task->labels()->saveMany($labels);
        }

        flash(__('Задача успешно создана'))->success();
        return redirect()->route('tasks.index');
    }


    /**
     * Show the form for editing the specified resource.
     *
     * @param  Task  $task
     * @return Response
     */
    public function edit(Task $task): Response
    {
        $statusesList = ['' => 'Выберите статус'] + TaskStatus::pluck('name', 'id')->all();
        $usersList = ['' => 'Выберите исполнителя'] + User::pluck('name', 'id')->all();
        $labelsList = ['' => 'Добавьте метки'] + Label::pluck('name', 'id')->all();

        return response()->view('tasks.edit', compact('task', 'statusesList', 'usersList', 'labelsList'));
    }

    /**
     * @param TaskRequest $request
     * @param Task $task
     * @return RedirectResponse
     */
    public function update(TaskRequest $request, Task $task): RedirectResponse
    {
        $data = $request->input();
        $task->fill($data);
        $task->save();

        if (isset($data['labels'])) {
            /** @var array */
            $labelsIds = array_filter($data['labels']);
            $task->labels()->sync($labelsIds);
        }
        flash(__('Задача успешно изменена'))->success();
        return redirect()->route('tasks.index');
    }

    /**
     * @param Task $task
     * @return RedirectResponse
     */
    public function destroy(Task $task): RedirectResponse
    {
        if (! Gate::allows('destroy-task', $task)) {
            throw new AccessDeniedHttpException('You can delete only own tasks');
        }
        $task->delete();
        flash(__('Задача успешно удалена'))->success();
        return redirect()->route('tasks.index');
    }
}
