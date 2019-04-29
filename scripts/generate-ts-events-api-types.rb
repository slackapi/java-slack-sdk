#!/usr/bin/env ruby
#
# prerequisites
#   npm install -g quicktype

require 'open3'
require __dir__ + '/lib/ts_writer.rb'

index_file = __dir__ + '/../typescript-types/events-api/index.ts'
File.truncate(index_file, 0)

ts_writer = TsWriter.new

Dir.glob(__dir__ + '/../typescript-types/source/events-api/*').sort.each do |json_path|
  File.open(json_path) do |json_file|
    filename = json_path.split('/').last.gsub(/\.json$/, '')
    root_class_name = filename
    typedef_filepath = __dir__ + "/../typescript-types/events-api/#{root_class_name}.d.ts"
    input_json = json_file.read
    ts_writer.write(root_class_name, json_path, typedef_filepath, input_json)
    ts_writer.append_to_index_ts(root_class_name, index_file)
  end
end
