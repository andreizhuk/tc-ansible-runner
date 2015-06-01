var AR2 = {
	containerId: "ar_results_container",
	load: function(url) {
		$j.getJSON(url, this.showReport);
	},
	showReport: function(playbooks) {
		$j(this.containerId).html(playbooks);
	}
} 