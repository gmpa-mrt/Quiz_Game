<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Question extends Model
{
    protected $table = 'question';
    public $fillable = [
        'wording'
    ];

    public function theme()
    {
        return $this->belongsTo('App\Models\Theme');
    }

    public function answer()
    {
        return $this->hasMany('App\Models\Answer');
    }
}
