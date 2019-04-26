#!/usr/bin/env ruby
#
# prerequisites
#   npm install -g quicktype

require 'open3'

index_file = __dir__ + '/../typescript-types/index.ts'
File.truncate(index_file, 0)

def write(root_class_name, json_path, typedef_filepath, input_json)
  cmd = "quicktype --just-types --all-properties-optional --acronym-style original -t #{root_class_name} -l ts -o #{typedef_filepath}"
  puts "Generating #{root_class_name} from #{json_path}"
  Open3.popen3(cmd) do |stdin, stdout, stderr, wait_thr|
    stdin.write(input_json)
  end
end

def append_to_index_ts(root_class_name, index_file, dir)
  File.open(index_file, 'a') do |index_f|
    index_f.puts("export { #{root_class_name} } from './#{dir}/#{root_class_name}';")
  end
end

# web-api
Dir.glob(__dir__ + '/../typescript-types/source/web-api/*').each do |json_path|
  File.open(json_path) do |json_file|
    root_class_name = ''
    prev_c = nil
    filename = json_path.split('/').last.gsub(/\.json$/, '')
    filename.split('').each do |c|
      if prev_c.nil? || prev_c == '.'
        root_class_name << c.upcase
      elsif c == '.'
        # noop
      else
        root_class_name << c
      end
      prev_c = c
    end
    root_class_name << 'Response'

    typedef_filepath = __dir__ + "/../typescript-types/web-api/#{root_class_name}.d.ts"
    input_json = json_file.read
    write(root_class_name, json_path, typedef_filepath, input_json)
    append_to_index_ts(root_class_name, index_file, 'web-api')
  end
end

# events-api
Dir.glob(__dir__ + '/../typescript-types/source/events-api/*').each do |json_path|
  File.open(json_path) do |json_file|
    filename = json_path.split('/').last.gsub(/\.json$/, '')
    root_class_name = "Events#{filename}"
    typedef_filepath = __dir__ + "/../typescript-types/events-api/#{root_class_name}.d.ts"
    input_json = json_file.read
    write(root_class_name, json_path, typedef_filepath, input_json)
    append_to_index_ts(root_class_name, index_file, 'events-api')
  end
end

# rtm-api
Dir.glob(__dir__ + '/../typescript-types/source/rtm-api/*').each do |json_path|
  File.open(json_path) do |json_file|
    filename = json_path.split('/').last.gsub(/\.json$/, '')
    root_class_name = "RTM#{filename}"
    typedef_filepath = __dir__ + "/../typescript-types/rtm-api/#{root_class_name}.d.ts"
    input_json = json_file.read
    write(root_class_name, json_path, typedef_filepath, input_json)
    append_to_index_ts(root_class_name, index_file, 'rtm-api')
  end
end