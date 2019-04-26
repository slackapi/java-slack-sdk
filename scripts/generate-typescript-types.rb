#!/usr/bin/env ruby
#
# prerequisites
#   npm install -g quicktype

require 'open3'

index_file = __dir__ + '/../typescript-types/index.ts'
File.truncate(index_file, 0)

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

    typedef_file = __dir__ + "/../typescript-types/web-api/#{root_class_name}.d.ts"
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

# events-api
Dir.glob(__dir__ + '/../typescript-types/source/events-api/*').each do |json_path|
  File.open(json_path) do |json_file|
    root_class_name = 'Events'
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

    typedef_file = __dir__ + "/../typescript-types/events-api/#{root_class_name}.d.ts"
    cmd = "quicktype --just-types --all-properties-optional --acronym-style original -t #{root_class_name} -l ts -o #{typedef_file}"
    input_json = json_file.read
    puts "Generating #{root_class_name} from #{json_path}"
    Open3.popen3(cmd) do |stdin, stdout, stderr, wait_thr|
      stdin.write(input_json)
    end

    File.open(index_file, 'a') do |index_f|
      index_f.puts("export { #{root_class_name} } from './events-api/#{root_class_name}';")
    end

  end
end

# rtm-api
Dir.glob(__dir__ + '/../typescript-types/source/rtm-api/*').each do |json_path|
  File.open(json_path) do |json_file|
    root_class_name = 'RTM'
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

    typedef_file = __dir__ + "/../typescript-types/rtm-api/#{root_class_name}.d.ts"
    cmd = "quicktype --just-types --all-properties-optional --acronym-style original -t #{root_class_name} -l ts -o #{typedef_file}"
    input_json = json_file.read
    puts "Generating #{root_class_name} from #{json_path}"
    Open3.popen3(cmd) do |stdin, stdout, stderr, wait_thr|
      stdin.write(input_json)
    end

    File.open(index_file, 'a') do |index_f|
      index_f.puts("export { #{root_class_name} } from './rtm-api/#{root_class_name}';")
    end

  end
end