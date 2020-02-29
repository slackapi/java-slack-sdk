<div id="page">
  <div id="page_contents" class="clearfix">
    <nav id="api_nav" class="col span_1_of_4">
      <div id="api_sections">
        {% include sidebar-ja.md %}
      </div>
    </nav>
    <div class="col span_3_of_4">
      <div class="card">
        {{ content | markdownify }}
        <div class="clear_both large_bottom_margin"></div>
      </div>
    </div>
  </div>
</div>
