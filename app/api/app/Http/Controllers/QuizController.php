<?php

namespace App\Http\Controllers;

use App\Quiz;
use Illuminate\Database\QueryException;
use Illuminate\Http\Request;

class QuizController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\JsonResponse
     */
    public function index()
    {
        $quiz = Quiz::all();
        return response()->json($quiz, 200);
    }

    /**
     * Show the form for creating a new resource.
     *
     * @return \Illuminate\Http\JsonResponse
     */
    public function create()
    {

    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\JsonResponse
     */
    public function store(Request $request)
    {
        $quiz = new Quiz();
        $quiz->score = $request->input('score');
        $quiz->user_id = $request->input('user_id');
        $quiz->theme_id = $request->input('theme_id');
        try {
            $quiz->save();
            return response()->json($quiz, 200);
        }catch(QueryException $e){
            return response()->json([
                'error'=> true,
                'message' => 'Failed to create the Quiz'
            ], 400);
        }
    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return array
     */
    public function show($id)
    {
        return ['quiz' => Quiz::findOrFail($id)];
    }

    /**
     * Show the form for editing the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function edit($id)
    {
        //
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  int  $id
     * @return \Illuminate\Http\JsonResponse
     */
    public function update(Request $request, $id)
    {
        $quiz = Quiz::find($id);
        if ($quiz == null) {
            return response()->json([
                'error' => true,
                'message' => "Quiz didn't find",
            ], 400);
        }
        $quiz->score = $request->input('score');
        $quiz->user_id = $request->input('user_id');
        $quiz->theme_id = $request->input('theme_id');

        try {
            $quiz->save();
            return response()->json($quiz, 200);
        } catch(\Illuminate\Database\QueryException $e) {
            return response()->json([
                'error' => true,
                'message' => "Error update quiz",
            ], 400);
        }
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy($id)
    {
        //
    }
}
