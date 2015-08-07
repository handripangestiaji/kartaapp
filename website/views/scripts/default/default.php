<div class="container">
    <div class="row row-eq-height">
        <div class="col-xs-12 col-md-4">
            <img src="/website/static/img/screen.png" alt="App Screenshot" class="app-screen">
        </div><!--/ .col-xs-12 -->
        <div class="col-xs-12 col-md-8">
            <div class="content-box">
                <h1>Find dishes specific to your <br /> dietary restriction with Karta.</h1>
                <p>
                    Discover and search restaurants and dishes around you.<br />
                    Filter by your dietary restriction.<br />
                    Rate and review any dish on any menu.
                </p>
                <form role="form" class="form-inline" onsubmit="return false;">
                    <label>Join Beta</label>
                    <div class="clearfix"></div>
                    <div class="form-group">
                        <input type="email" name="email" id="email" class="form-control" required tabindex="1" size="32" maxlength="48" placeholder="Enter your email address">
                    </div><!--/ .form-group -->
                    <div class="form-group">
                        <input type="submit" name="submit" class="btn btn-primary" value="Join" id="join">
                    </div><!--/ .form-group -->
                </form>
                <div class="loading" style="display: none;">
	                <img src="/website/static/img/ajax-loader.gif" class="img-responsive"/>
                </div>
                <div class="thankyou-box" style="display: none;">
	                <div class="thankyou-box--content mt24">
	                    <p>Thank you for registering your e-mail, check your inbox to get the app.</p>
	                </div><!--/ .thankyou-box--content -->
	            </div><!--/ .thankyou-box -->
	            <div class="registered-box" style="display: none;">
	                <div class="thankyou-box--content mt24">
	                    <p>Great news! You've already signed up!</p>
	                </div><!--/ .thankyou-box--content -->
	            </div><!--/ .thankyou-box -->
                <a href="#" target="_blank" class="btn-google-play">
                    <img src="/website/static/img/google-play-badge.png" alt="Get in on Google Play" class="img-responsive">
                </a><!--/ .btn-google-play -->
            </div><!--/ .content-box -->
        </div><!--/ .col-xs-12 -->
    </div><!--/ .row -->
</div><!--/ .container -->