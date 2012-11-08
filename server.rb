require 'rubygems'
require 'sinatra'
require 'json'

get("/tweets") do
  [
    {
      :status => 'howdy!',
      :screen_name => 'twoism'
    }
  ].to_json
end
