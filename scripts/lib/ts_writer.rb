# prerequisites
#   npm install -g quicktype

class TsWriter
  def write(root_class_name, json_path, typedef_filepath, input_json)
    cmd = "quicktype --just-types --all-properties-optional --acronym-style original -t #{root_class_name} -l ts -o #{typedef_filepath}"
    puts "Generating #{root_class_name} from #{json_path}"
    Open3.popen3(cmd) do |stdin, stdout, stderr, wait_thr|
      stdin.write(input_json)
    end
  end

  def append_to_index_ts(root_class_name, index_file)
    File.open(index_file, 'a') do |index_f|
      index_f.puts("export { #{root_class_name} } from './#{root_class_name}';")
    end
  end
end