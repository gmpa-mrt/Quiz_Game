<?php

namespace App\Http\Controllers;

use App\Answer;
use Illuminate\Database\QueryException;
use Illuminate\Http\JsonResponse;
use Illuminate\Http\Request;
use Illuminate\Http\Response;
use Illuminate\Support\Facades\DB;

class AnswerController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return JsonResponse
     */
    public function index()
    {
        $answer = Answer::all();
        return response()->json($answer, 200);
    }

    /**
     * Show the form for creating a new resource.
     *
     * @return Response
     */
    public function create()
    {
        //
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param Request $request
     * @return JsonResponse
     */
    public function store(Request $request)
    {
        $answer = new Answer();
        $answer->text = $request->input('text');
        $answer->correct = $request->input('correct');
        $answer->question_id = $request->input('question_id');
        $answer->theme_id = $request->input('theme_id');

        try {
            $answer->save();
            return response()->json($answer, 200);
        } catch (QueryException $e) {
            return response()->json([
                'error' => true,
                'message' => 'Answer invalid' . $e->getMessage()
            ], 400);
        }
    }


    /**
     * Display the specified resource.
     *
     * @param int $id
     * @return array
     */
    public function show($id)
    {
        return ['answer' => Answer::findOrFail($id)];
    }

    /**
     * Show the form for editing the specified resource.
     *
     * @param int $id
     * @return Response
     */
    public function edit($id)
    {
        //
    }

    /**
     * Update the specified resource in storage.
     *
     * @param Request $request
     * @param int $id
     * @return Response
     */
    public function update(Request $request, $id)
    {
        //
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param int $id
     * @return Response
     */
    public function destroy($id)
    {
        //
    }
}
