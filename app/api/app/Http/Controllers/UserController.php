<?php

namespace App\Http\Controllers;

use App\User;
use Illuminate\Http\JsonResponse;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Hash;

class UserController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return JsonResponse
     */
    public function index()
    {
        $user= User::all();
        return response()->json($user, 200);
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param Request $request
     * @return JsonResponse
     */
    public function store(Request $request)
    {
        $user = new User();
        $user->pseudo = $request->input('pseudo');
        $user->fill(['password' => Hash::make($request->input('password'))]);
        try {
            $user->save();
            return response()->json($user, 201);
        }catch (\Illuminate\Database\QueryException $e){
            return response()->json([
                'error' => true,
                'message' => "Please don't let some fields empty"
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
       return ['user' => User::findOrFail($id)];
    }

    /**
     * Authentication
     * @param Request $request
     * @return JsonResponse
     */
    public function auth(Request $request) {
        $user = User::where('pseudo', '=', $request->input('pseudo'))->first();
        if ($user == null)
            return response()->json([
                'error' => true,
                'message' => "Wrong pseudo/password",
            ], 400);
        else if (Hash::Check($request->input('password'), $user->password) == false) {
            return response()->json([
                'error' => true,
                'message' => "Wrong pseudo/password"
            ], 400);
        }
        else
            return response()->json([
                'error' => false,
                'message' => 'OK user allowed',
                'id' => $user->id
            ], 200);
    }
    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return JsonResponse
     */
    public function destroy($id)
    {
        User::where('id', '=', $id)->delete();

        if (User::destroy($id) == 0)
            return response()->json([
                'error' => true,
                'message' => "User not found",
            ], 400);
        else
            return response()->json('OK', 200);
    }
}
