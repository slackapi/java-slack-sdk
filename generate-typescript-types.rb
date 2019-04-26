#!/usr/bin/env ruby
#
# prerequisites
#   npm install -g quicktype

require 'open3'

index_file = 'typescript-types/index.ts'
File.truncate(index_file, 0)

Dir.glob('json-logs/samples/api/*').each do |json_path|
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

    typedef_file = "typescript-types/web-api/#{root_class_name}.d.ts"
    cmd = "quicktype --just-types --all-properties-optional --acronym-style original -t #{root_class_name} -l ts -o #{typedef_file}"
    input_json = json_file.read
    puts "Generating #{root_class_name} from #{json_path}"
    Open3.popen3(cmd) do |stdin, stdout, stderr, wait_thr|
      stdin.write(input_json)
    end

    File.open(index_file, 'a') do |index_f|
      index_f.puts("export { #{root_class_name} } from './web-api/#{root_class_name}';")
    end

  end
end
