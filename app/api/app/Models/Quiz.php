<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Quiz extends Model
{
    protected $table = 'quiz';
    public $fillable = [
        'score'
    ];

    public function user()
    {
        return $this->belongsTo('App\Models\User');
    }

    public function theme()
    {
        return $this->belongsTo('App\Models\Theme');
    }
}
