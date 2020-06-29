<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Answer extends Model
{
    protected $table = 'answer';
    public $fillable = [
        'text', 'correct'
    ];

    public function question()
    {
        return $this->belongsTo('App\Models\Question');
    }
}
