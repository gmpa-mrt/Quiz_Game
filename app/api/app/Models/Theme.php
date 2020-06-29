<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Theme extends Model
{
    protected $table = 'theme';
    public $fillable = [
        'name'
    ];


    public function question()
    {
        return $this->hasMany('App\Models\Question');
    }

    public function quiz()
    {
        return $this->hasMany('App\Models\Quiz');
    }
}
